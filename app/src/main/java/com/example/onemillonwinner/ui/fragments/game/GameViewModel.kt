package com.example.onemillonwinner.ui.fragments.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.data.GameQuestion
import com.example.onemillonwinner.data.GameQuestionList
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.network.Repository
import com.example.onemillonwinner.ui.base.BaseViewModel
import com.example.onemillonwinner.util.Constants.QUESTION_TIME
import com.example.onemillonwinner.util.extension.addTo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit


class GameViewModel : BaseViewModel() {

    private val triviaQuestions: GameQuestionList by lazy { GameQuestionList() }
    private val repository: Repository by lazy { Repository() }
    private lateinit var timerDisposable: Disposable

    val isChangeQuestion = MutableLiveData(false)
    val isDeleteHalfOfAnswers = MutableLiveData(false)
    val isHelpByFriends = MutableLiveData(false)
    var isAudienceVote = MutableLiveData(false)

    private val _gameState = MutableLiveData<GameState>()
    val state: LiveData<GameState>
        get() = _gameState

    private val _question = MutableLiveData<GameQuestion>()
    val question: LiveData<GameQuestion>
        get() = _question

    private val _questionTime = MutableLiveData(QUESTION_TIME)
    val questionTime: LiveData<Int>
        get() = _questionTime

    private val _prize = MutableLiveData(0)
    val prize: LiveData<Int>
        get() = _prize

    private val questionTimeOver = MutableLiveData(false)

    val onEventClick = MutableLiveData<OnEventClick>()

    init {
        _gameState.postValue(GameState.Loading)
        repository.getAllQuestions()
            .subscribe(::onSuccessUpdateQuestion, ::onErrorUpdateQuestion).addTo(disposable)
    }

    private fun onSuccessUpdateQuestion(state: State<TriviaResponse>) {
        _gameState.postValue(GameState.Success)
        timer()
        state.toData()?.let {
            triviaQuestions.setQuestions(it.questions)
            updateView()
        }
    }

    private fun onErrorUpdateQuestion(throwable: Throwable) {
        _gameState.postValue(GameState.Failure)
    }

    fun onClickToUpdateView() {
        when (_gameState.value) {
            GameState.ANSWER_SELECTED -> {
                showAnswer()
            }
            GameState.QUESTION_SUBMITTED -> {
                updateView()
            }
            GameState.WRONG_ANSWER_SUBMITTED -> {
                _gameState.postValue(GameState.GameOver)
                onEventClick.postValue(OnEventClick.WhenTheGameEnd)
            }
            else -> {
                // Toast please select answer or exit.
            }
        }
    }

    private fun calculatePrize() {
        _prize.postValue(triviaQuestions.getPrize())
    }

    private fun showAnswer() {
        setGameState()
        calculatePrize()
        _question.postValue(triviaQuestions.getCurrentQuestion())
        timerDisposable.dispose()
    }

    private fun setGameState() {
        if (triviaQuestions.isGameOver())
            _gameState.postValue(GameState.WRONG_ANSWER_SUBMITTED)
        else
            _gameState.postValue(GameState.QUESTION_SUBMITTED)
    }


    private fun updateView() {
        if (!triviaQuestions.isGameOver()) {
            _gameState.postValue(GameState.QUESTION_START)
            _question.postValue(triviaQuestions.updateQuestion())
            restartTimer()
        } else {
            timerDisposable.dispose()
            _gameState.postValue(GameState.GameOver)
            onEventClick.postValue(OnEventClick.WhenTheGameEnd)
        }
    }

    fun updateGameState(state: GameState) {
        _gameState.postValue(state)
    }

    fun replaceQuestion() {
        if (_gameState.value != GameState.QUESTION_SUBMITTED
            && _gameState.value != GameState.WRONG_ANSWER_SUBMITTED
        ) {
            _gameState.postValue(GameState.QUESTION_START)
            isChangeQuestion.postValue(true)
            _question.postValue(triviaQuestions.replaceQuestion())
            restartTimer()
        }
    }


    private fun restartTimer() {
        timerDisposable.dispose()
        timer()
    }

    fun deleteHalfOfAnswers() {
        if (_gameState.value != GameState.QUESTION_SUBMITTED
            && _gameState.value != GameState.WRONG_ANSWER_SUBMITTED
        ) {
            _gameState.postValue(GameState.QUESTION_START)
            isDeleteHalfOfAnswers.postValue(true)
            _question.postValue(triviaQuestions.deleteTwoWrongAnswersRandomly())
        }
    }

    fun helpByFriends() {
        onEventClick.postValue(OnEventClick.OnClickFriendCall)
        isHelpByFriends.postValue(true)
    }

    fun audienceVoteClick(){
        isAudienceVote.postValue(true)
        onEventClick.postValue(OnEventClick.OnAudienceVoteHelpToolClick)
    }

    private fun timer(questionTimer: Int = QUESTION_TIME) {
        _questionTime.postValue(questionTimer)
        val timeInSecond: Long = questionTimer.toLong()
        timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .take(timeInSecond).map {
                ((timeInSecond - 1) - it)
            }.subscribe {
                _questionTime.postValue(it.toInt())
                if (it.toInt() == 0) {
                    endTheCountDown()
                }
            }
    }

    private fun endTheCountDown() {
        timerDisposable.dispose()
        _gameState.postValue(GameState.GameOver)
        onEventClick.postValue(OnEventClick.WhenTheGameEnd)
        questionTimeOver.postValue(true)
    }

    fun getFriendHelp() = triviaQuestions.getFriendHelp()

    // Also you can send data to fragment by add data class
    sealed class OnEventClick {
        object OnClickFriendCall: OnEventClick()
        object OnAudienceVoteHelpToolClick : OnEventClick()
        object WhenTheGameEnd: OnEventClick()
    }
}


package com.example.onemillonwinner.ui.fragments.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.GameQuestion
import com.example.onemillonwinner.data.GameQuestionList
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.network.Repository
import com.example.onemillonwinner.util.Constants.QUESTION_TIME
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit


class GameViewModel : ViewModel() {

    private val questionLogic: GameQuestionList by lazy { GameQuestionList() }
    private val repository: Repository by lazy { Repository() }
    private lateinit var timerDisposable: Disposable

    var isChangeQuestion = MutableLiveData(false)
    var isDeleteHalfOfAnswers = MutableLiveData(false)
    var isHelpByFriends = MutableLiveData(false)

    private val _gameState = MutableLiveData<GameState>()
    val state: LiveData<GameState>
        get() = _gameState

    private val _question = MutableLiveData<GameQuestion>()
    val question: LiveData<GameQuestion>
        get() = _question

    private val _questionTime = MutableLiveData(QUESTION_TIME)
    val questionTime: LiveData<Int>
        get() = _questionTime

    private val questionTimeOver = MutableLiveData(false)


    init {
        _gameState.postValue(GameState.Loading)
        repository.getAllQuestions().subscribe(::onSuccessUpdateQuestion, ::onErrorUpdateQuestion)
    }

    private fun onSuccessUpdateQuestion(state: State<TriviaResponse>) {
        _gameState.postValue(GameState.Success)
        timer()
        state.toData()?.let {
            questionLogic.setQuestions(it.questions)
            updateView()
        }
    }

    private fun onErrorUpdateQuestion(throwable: Throwable) {
        _gameState.postValue(GameState.Failure)
    }

    fun onClickToUpdateView() {
        if (_gameState.value == GameState.ANSWER_SELECTED) {
            showAnswer()
        } else if (_gameState.value == GameState.QUESTION_SUBMITTED) {
            updateView()
        }
    }

    private fun showAnswer() {
        _gameState.postValue(GameState.QUESTION_SUBMITTED)
        _question.postValue(questionLogic.getCurrentQuestion())
        timerDisposable.dispose()
    }

    private fun updateView() {
        if (!questionLogic.isGameDone()) {
            _gameState.postValue(GameState.QUESTION_START)
            _question.postValue(questionLogic.updateQuestion())
            restartTimer()
        } else {
            timerDisposable.dispose()
            _gameState.postValue(GameState.GameOver)
        }
    }

    fun updateGameState(state: GameState) {
        _gameState.postValue(state)
    }

    fun replaceQuestion() {
        if (_gameState.value != GameState.QUESTION_SUBMITTED) {
            _gameState.postValue(GameState.QUESTION_START)
            isChangeQuestion.postValue(true)
            _question.postValue(questionLogic.replaceQuestion())
            restartTimer()
        }
    }

    private fun restartTimer() {
        timerDisposable.dispose()
        timer()
    }

    fun deleteHalfOfAnswers() {
        _gameState.postValue(GameState.QUESTION_START)
        isDeleteHalfOfAnswers.postValue(true)
        _question.postValue(questionLogic.deleteTwoWrongAnswersRandomly())
    }

    fun helpByFriends() {
        isHelpByFriends.postValue(true)
    }

    private fun timer() {
        _questionTime.postValue(QUESTION_TIME)
        val timeInSecond: Long = QUESTION_TIME.toLong()
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
        questionTimeOver.postValue(true)
    }

}


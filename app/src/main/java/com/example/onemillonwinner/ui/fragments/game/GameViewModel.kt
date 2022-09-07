package com.example.onemillonwinner.ui.fragments.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.data.*
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.network.Repository
import com.example.onemillonwinner.ui.base.BaseViewModel
import com.example.onemillonwinner.util.Constants.QUESTION_TIME
import com.example.onemillonwinner.util.extension.addTo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class GameViewModel : BaseViewModel() {

    private val triviaQuestions: TriviaQuestion by lazy { TriviaQuestion() }
    private val repository: Repository by lazy { Repository() }
    private lateinit var timerDisposable: Disposable

    val isChangeQuestion = MutableLiveData(false)
    val isDeleteHalfOfAnswers = MutableLiveData(false)
    val isHelpByFriends = MutableLiveData(false)

    private val _responseState = MutableLiveData<State<TriviaResponse>>()
    val responseState: LiveData<State<TriviaResponse>>
        get() = _responseState

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


    init {
        _responseState.postValue(State.Loading)
        repository.getAllQuestions().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(::onSuccessUpdateQuestion, ::onErrorUpdateQuestion).addTo(disposable)
    }

    private fun onSuccessUpdateQuestion(state: State<TriviaResponse>) {
        _responseState.postValue(State.Success(state.toData()))
        startTimer()
        state.toData()?.let {
            triviaQuestions.setQuestions(it.questions)
            updateView()
        }
    }

    private fun onErrorUpdateQuestion(throwable: Throwable) {
        _responseState.postValue(State.Failure(throwable.message.toString()))
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
            }
            else -> {

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
        startTimer()
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
        isHelpByFriends.postValue(true)
    }

    private fun startTimer() {
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
        triviaQuestions.getCurrentQuestion().setSelectedAnswer(-1)
        _prize.postValue(triviaQuestions.getPrize())
        _gameState.postValue(GameState.GameOver)
    }

    fun getFriendHelp() = triviaQuestions.getFriendHelp()

}


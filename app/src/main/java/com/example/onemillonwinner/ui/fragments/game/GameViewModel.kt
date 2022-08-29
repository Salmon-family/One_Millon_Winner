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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit


class GameViewModel : ViewModel() {

    private val questionLogic: GameQuestionList by lazy { GameQuestionList() }
    private val repository: Repository by lazy { Repository() }
    lateinit var timerDisposable: Disposable

    private val _gameState = MutableLiveData<GameState>()
    val state: LiveData<GameState>
        get() = _gameState

    private val _question = MutableLiveData<GameQuestion>()
    val question: LiveData<GameQuestion>
        get() = _question

    private val _questionTime = MutableLiveData(100)
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
            timerDisposable.dispose()
            timer()
        } else {
            timerDisposable.dispose()
            _gameState.postValue(GameState.GameComplete)
        }
    }

    fun updateGameState(state: GameState) {
        when (state) {
            GameState.REPLACING_QUESTION -> {
                if (!questionLogic.isReplaceQuestionUsed()) {
                    replaceQuestion()
                } else {
                    /**
                     * Toast or anything tell user that can used only one time.
                     * */
                }
            }

            else -> {}
        }
        _gameState.postValue(state)
    }

    private fun replaceQuestion() {
        _gameState.postValue(GameState.QUESTION_START)
        _question.postValue(questionLogic.replaceQuestion())
        timerDisposable.dispose()
        timer()
    }

    private fun timer() {
        _questionTime.postValue(100)
        val timeInSecond: Long = 100
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
        questionTimeOver.postValue(true)
    }

}


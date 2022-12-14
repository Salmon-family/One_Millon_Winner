package com.example.onemillonwinner.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.data.*
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.data.network.Repository
import com.example.onemillonwinner.ui.base.BaseViewModel
import com.example.onemillonwinner.util.Constants.MAX_NUMBER_OF_QUESTIONS
import com.example.onemillonwinner.util.Constants.NUMBER_OF_QUESTIONS_PER_LEVEL
import com.example.onemillonwinner.util.Constants.QUESTION_TIME
import com.example.onemillonwinner.util.Event
import com.example.onemillonwinner.util.enumState.QuestionState
import com.example.onemillonwinner.util.extension.addTo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class GameViewModel : BaseViewModel() {

    private val triviaQuestions: TriviaQuestion = TriviaQuestion()
    private val repository: Repository = Repository()
    private lateinit var timerDisposable: Disposable

    val isChangeQuestionClicked = MutableLiveData(false)
    val isDeleteHalfOfAnswersClicked = MutableLiveData(false)
    val isCallingFriendClicked = MutableLiveData(Event(false))

    private val _gameState = MutableLiveData<State<TriviaResponse>>()
    val state: LiveData<State<TriviaResponse>>
        get() = _gameState

    private val _questionState = MutableLiveData<QuestionState>()
    val questionState: LiveData<QuestionState>
        get() = _questionState

    private val _question = MutableLiveData<GameQuestion>()
    val question: LiveData<GameQuestion>
        get() = _question

    private val _questionTime = MutableLiveData(QUESTION_TIME)
    val questionTime: LiveData<Int>
        get() = _questionTime

    private val _prize = MutableLiveData(Prize(0, false))
    val prize: LiveData<Prize>
        get() = _prize

    private val _choices = MutableLiveData<List<Choice>>()
    val choices: LiveData<List<Choice>>
        get() = _choices

    init {
        _gameState.postValue(State.Loading)
        repository.getAllQuestions().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(::onSuccessUpdateQuestion, ::onErrorUpdateQuestion).addTo(disposable)
    }

    private fun onSuccessUpdateQuestion(state: State<TriviaResponse>) {
        state.toData()?.let {
            triviaQuestions.setQuestion(it.questions)
            if (triviaQuestions.getQuestionSize() >= MAX_NUMBER_OF_QUESTIONS) {
                startTimer()
                _gameState.postValue(State.Success(it))
                updateView()

            }
        }
    }

    private fun onErrorUpdateQuestion(throwable: Throwable) {
        _gameState.postValue(State.Failure(throwable.message.toString()))
    }

    private fun showAnswer() {
        displayCorrectAnswer()
        setGameState()
        calculatePrize()
        _question.postValue(triviaQuestions.getCurrentQuestion())
        timerDisposable.dispose()
    }

    private fun updateView() {
        if (!triviaQuestions.isGameOver()) {
            _questionState.postValue(QuestionState.QUESTION_START)
            _question.postValue(triviaQuestions.updateQuestion())
            _choices.postValue(triviaQuestions.getCurrentQuestion().answers)
            restartTimer()
        } else {
            timerDisposable.dispose()
            _questionState.postValue(QuestionState.GAME_OVER)
        }
    }

    private fun setGameState() {
        if (triviaQuestions.isGameOver())
            _questionState.postValue(QuestionState.WRONG_ANSWER_SUBMITTED)
        else
            _questionState.postValue(QuestionState.QUESTION_SUBMITTED)
    }

    private fun calculatePrize() {
        val isSecured =
            triviaQuestions.getCurrentQuestion().questionNumber % NUMBER_OF_QUESTIONS_PER_LEVEL == 0
        val prizeValue = triviaQuestions.getPrize()
        prizeValue?.let {
            _prize.postValue(Prize(prizeValue, isSecured))
        }
    }

    private fun displayCorrectAnswer() {
        _choices.postValue(triviaQuestions.getCurrentQuestion().setCorrectAnswer())
    }

    fun onClickToUpdateView() {
        _questionState.value?.let { state ->
            when (state) {
                QuestionState.QUESTION_SUBMITTED -> {
                    updateView()
                }
                QuestionState.WRONG_ANSWER_SUBMITTED -> {
                    _questionState.postValue(QuestionState.GAME_OVER)
                }
                QuestionState.QUESTION_START -> {
                    if (triviaQuestions.getCurrentQuestion().isAnswerSelected()) {
                        showAnswer()
                    }
                }
                else -> {}
            }
        }
    }

    fun updateChoice(choiceNumber: Int) {
        _choices.postValue(triviaQuestions.getCurrentQuestion().updateChoice(choiceNumber))
    }

    private fun restartTimer() {
        timerDisposable.dispose()
        startTimer()
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
        triviaQuestions.getCurrentQuestion().removeAllSelection()
        calculatePrize()
        _questionState.postValue(QuestionState.GAME_OVER)
    }

    fun getFriendHelp() = triviaQuestions.getCurrentQuestion().getCorrectAnswer()

    fun helpByFriends() {
        isCallingFriendClicked.postValue(Event(true))
    }

    fun replaceQuestion() {
        if (_questionState.value != QuestionState.QUESTION_SUBMITTED
            && _questionState.value != QuestionState.WRONG_ANSWER_SUBMITTED
        ) {
            isChangeQuestionClicked.postValue(true)
            _questionState.postValue(QuestionState.QUESTION_START)
            _question.postValue(triviaQuestions.replaceQuestion())
            _choices.postValue(triviaQuestions.getCurrentQuestion().answers)

            restartTimer()
        }
    }

    fun deleteHalfOfAnswers() {
        if (_questionState.value != QuestionState.QUESTION_SUBMITTED
            && _questionState.value != QuestionState.WRONG_ANSWER_SUBMITTED
        ) {
            _questionState.postValue(QuestionState.QUESTION_START)
            isDeleteHalfOfAnswersClicked.postValue(true)
            _choices.postValue(triviaQuestions.getCurrentQuestion().deleteTwoWrongAnswersRandomly())
            _question.postValue(triviaQuestions.getCurrentQuestion())
        }
    }

}
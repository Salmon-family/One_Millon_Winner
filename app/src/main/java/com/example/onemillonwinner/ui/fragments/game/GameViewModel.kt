package com.example.onemillonwinner.ui.fragments.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.GameQuestion
import com.example.onemillonwinner.data.GameQuestionList
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

    var isChangeQuestion = MutableLiveData(false)
    var isDeleteHalfOfAnswers = MutableLiveData(false)
    var isHelpByFriends = MutableLiveData(false)

    private val _gameState = MutableLiveData<State<TriviaResponse>>()
    val state: LiveData<State<TriviaResponse>>
        get() = _gameState

    private val _question = MutableLiveData<GameQuestion>()
    val question: LiveData<GameQuestion>
        get() = _question

    private val _questionsLiveData = MutableLiveData<State<TriviaResponse>>()
    val questions: LiveData<State<TriviaResponse>>
        get() = _questionsLiveData

    private val _questionTime = MutableLiveData(100)
    val questionTime: LiveData<Int>
        get() = _questionTime

    val questionTimeOver = MutableLiveData(false)

    private val _prize = MutableLiveData("$0")
    val prize: LiveData<String>
        get() = _prize
  


    init {
        _gameState.postValue(State.Loading)
        repository.getAllQuestions().subscribe(::onSuccessUpdateQuestion, ::onErrorUpdateQuestion)
    }

    private fun onSuccessUpdateQuestion(state: State<TriviaResponse>) {
        _gameState.postValue(state)
        timer()
        state.toData()?.let {
            questionLogic.setQuestions(it.questions)
            updateView()
        }
    }

    private fun onErrorUpdateQuestion(throwable: Throwable) {

    }

    fun onClickToUpdateView() {
        if (questionLogic.isReadyToSubmit()) {
            questionLogic.setCurrentQuestionSubmitted(true)
            _question.postValue(questionLogic.getCurrentQuestion())
            timerDisposable.dispose()
        } else if (questionLogic.isCurrentQuestionSubmitted()) {
            questionLogic.setCurrentQuestionSubmitted(false)
            updateView()
        }
    }

    private fun updateView() {
        if (!questionLogic.isGameDone()) {
            _question.postValue(questionLogic.updateQuestion())
            timerDisposable.dispose()
            timer()
       calculatePrize()
        } else {
            timerDisposable.dispose()
            _gameState.postValue(State.Complete)
        }
    }

    private fun calculatePrize() {
        when(question.value?.getQuestionNumber()) {
            5 -> _prize.postValue("$1,000")
            10 -> _prize.postValue("$32,000")
            15 -> _prize.postValue("$1,000,000")
        }
    }

    fun onClickAnswer(selectedAnswerIndex: Int) {
        questionLogic.setSelectedAnswer(selectedAnswerIndex)
    }

    fun changeQuestion() {
        isChangeQuestion.postValue(true)
    }

    fun deleteHalfOfAnswers() {
        isDeleteHalfOfAnswers.postValue(true)
    }

    fun helpByFriends() {
        isHelpByFriends.postValue(true)
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


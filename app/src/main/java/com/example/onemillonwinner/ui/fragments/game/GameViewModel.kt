package com.example.onemillonwinner.ui.fragments.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.network.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit


class GameViewModel : ViewModel() {

    var isChangeQuestion = MutableLiveData(false)
    var isDeleteHalfOfAnswers = MutableLiveData(false)
    var isHelpByFriends = MutableLiveData(false)

    private val _questionsLiveData = MutableLiveData<State<TriviaResponse>>()
    val questions: LiveData<State<TriviaResponse>>
        get() = _questionsLiveData

    private val _questionTime = MutableLiveData<Int>(100)
    val questionTime : LiveData<Int>
        get() = _questionTime


    val questionTimeOver = MutableLiveData(false)

    private val questionLogic: GameQuestionList by lazy { GameQuestionList() }
    private val repository: Repository by lazy { Repository() }

    private val _gameState = MutableLiveData<State<TriviaResponse>>()
    val state: LiveData<State<TriviaResponse>>
        get() = _gameState

    private val _question = MutableLiveData<GameQuestion>()
    val question: LiveData<GameQuestion>
        get() = _question

    init {
        timer()
        _gameState.postValue(State.Loading)
        repository.getAllQuestions().subscribe(::onSuccessUpdateQuestion, ::onErrorUpdateQuestion)
    }

    private fun onSuccessUpdateQuestion(state: State<TriviaResponse>) {
        _gameState.postValue(state)
        state.toData()?.let {
            questionLogic.setQuestions(it.questions)
            getNextQuestion()
        }
    }

    private fun onErrorUpdateQuestion(throwable: Throwable) {

    }

    fun getNextQuestion() {
        if (!questionLogic.isGameDone()) {
            _question.postValue(questionLogic.updateQuestion())
        }else{
            _gameState.postValue(State.Complete)
        }
    }


    fun changeQuestion() {
        isChangeQuestion.value = true
    }

    fun deleteHalfOfAnswers() {
        isDeleteHalfOfAnswers.value = true
    }

    fun helpByFriends() {
        isHelpByFriends.value = true
    }


    private fun timer(): Disposable {
        val timeInSecond: Long = 100
        return Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .take(timeInSecond).map {
                ((timeInSecond - 1) - it)
            }.subscribe {
                _questionTime.postValue(it.toInt())
                if(it.toInt() == 0){
                    endTheCountDown()
                }
            }
    }

    private fun endTheCountDown() {
        questionTimeOver.postValue(true)
    }


}


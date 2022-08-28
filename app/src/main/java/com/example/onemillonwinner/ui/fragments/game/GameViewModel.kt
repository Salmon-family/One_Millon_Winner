package com.example.onemillonwinner.ui.fragments.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.enum.QuestionLevel
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.network.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit


class GameViewModel : ViewModel() {
    private val repository = Repository()

    private val _questionsLiveData = MutableLiveData<State<TriviaResponse>>()
    val questions: LiveData<State<TriviaResponse>>
        get() = _questionsLiveData

    private val _questionTime = MutableLiveData<Int>(100)
    val questionTime : LiveData<Int>
        get() = _questionTime


    val questionTimeOver = MutableLiveData(false)

    init {
        timer()
    }


    fun getTriviaQuestions() {
        _questionsLiveData.postValue(State.Loading)

        repository.getQuestion(5, QuestionLevel.EASY).subscribe(
            {
                _questionsLiveData.postValue(it)
                Log.v("testApi", it.toString())
            }, {
                Log.v("testApi", it.message.toString())
            }
        )
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


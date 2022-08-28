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
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class GameViewModel : ViewModel() {
    private val repository = Repository()

    private val _questionsLiveData = MutableLiveData<State<TriviaResponse>>()
    val questions: LiveData<State<TriviaResponse>>
        get() = _questionsLiveData

    val questionTime = MutableLiveData<Int>()
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
        val timeInMillisecond: Long = 100
        return Observable.interval(timeInMillisecond, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .take(timeInMillisecond).map {
                ((timeInMillisecond - 1) - it)
            }.subscribe {
                questionTime.postValue(it.toInt())
                if(it.toInt() == 0){
                    questionTimeIsUp()
                }
            }
    }

    private fun questionTimeIsUp() {
        questionTimeOver.value = true
    }


}


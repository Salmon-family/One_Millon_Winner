package com.example.onemillonwinner.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.data.NetworkStatus
import com.example.onemillonwinner.data.TriviaResponse
import com.example.onemillonwinner.data.enum.QuestionLevel
import io.reactivex.rxjava3.core.Single

class Repository {
    private val _questions = MutableLiveData<NetworkStatus<TriviaResponse>>()
    val questions: LiveData<NetworkStatus<TriviaResponse>>
        get() = _questions

    fun getQuestions(numberOfQuestions: Int) {
        Network.triviaService.getQuestions(
            questionNumbers = numberOfQuestions,
            QuestionDifficulty = QuestionLevel.EASY.value
        ).subscribe(::onSuccess, ::onError)
    }


    private fun onSuccess(response: TriviaResponse) {
        _questions.postValue(NetworkStatus.Success(response))
    }

    private fun onError(throwable: Throwable) {
        _questions.postValue(NetworkStatus.Failure(throwable.message.toString()))
    }
}
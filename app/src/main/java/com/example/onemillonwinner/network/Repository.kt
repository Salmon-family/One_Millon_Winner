package com.example.onemillonwinner.network

import com.example.onemillonwinner.data.NetworkStatus
import com.example.onemillonwinner.data.TriviaResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class Repository {

//    fun getQuestion(numberOfQuestions: Int): Observable<NetworkStatus<TriviaResponse>> {
//        return wrapperWithRXJava(Network.triviaService::getQuestions)
//    }

    private fun <T> wrapperWithRXJava(function: () -> Response<T>): Observable<NetworkStatus<T>> {
        return Observable.create { emitter ->
            emitter.onNext(NetworkStatus.Loading)
            val result = function()
            try {
                if (result.isSuccessful) {
                    emitter.onNext(NetworkStatus.Success(result.body()))
                } else {
                    emitter.onNext(NetworkStatus.Failure(result.message().toString()))
                }
            } catch (e: Exception) {
                emitter.onNext(NetworkStatus.Failure(e.message.toString()))
            }
        }
    }

}
package com.example.onemillonwinner.network

import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.util.ApiConstants.NUMBER_OF_QUESTIONS_PER_REQUEST
import com.example.onemillonwinner.util.enum.QuestionLevel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class Repository {

    fun getAllQuestions(): Observable<State<TriviaResponse>> {
        return wrapperWithState {
            Observable.fromIterable(QuestionLevel.values().toList())
                .concatMapSingle {
                    Api.triviaService.getQuestions(NUMBER_OF_QUESTIONS_PER_REQUEST, it.value)
                }
        }
    }

    private fun <T> wrapperWithState(function: () -> Observable<Response<T>>): Observable<State<T>> {
        return function().map {
            if (it.isSuccessful) {
                State.Success(it.body())
            } else {
                State.Failure(it.message())
            }
        }
    }

}

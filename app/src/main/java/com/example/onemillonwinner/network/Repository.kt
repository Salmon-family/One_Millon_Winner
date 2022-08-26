package com.example.onemillonwinner.network

import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.data.enum.QuestionLevel
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class Repository {

    fun getAllQuestions(): Maybe<State<TriviaResponse>> {
        return wrapperWithState {
            Observable.fromIterable(QuestionLevel.values().asList())
                .flatMapSingle {
                    Api.triviaService.getQuestions(5, it.value)
                }
                .reduce { x, y ->
                    combineResult(x, y)
                }
        }
    }

    private fun combineResult(
        firstResponse: Response<TriviaResponse>,
        secondResponse: Response<TriviaResponse>
    ): Response<TriviaResponse> {
        firstResponse.body().apply {
            secondResponse.body()?.questions?.let { this?.questions?.addAll(it) }
        }
        return firstResponse
    }

    private fun <T> wrapperWithState(function: () -> Maybe<Response<T>>): Maybe<State<T>> {
        return function().map {
            if (it.isSuccessful) {
                State.Success(it.body())
            } else {
                State.Failure(it.message())
            }
        }
    }

}

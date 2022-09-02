package com.example.onemillonwinner.network

import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.util.NetworkConstants.NUMBER_OF_QUESTIONS_PER_REQUEST
import com.example.onemillonwinner.util.enum.QuestionLevel
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class Repository {

    fun getAllQuestions(): Observable<State<TriviaResponse>> {
        return wrapperWithState {
            Observable.fromIterable(QuestionLevel.values().asList())
                .flatMapSingle {
                    Api.triviaService.getQuestions(NUMBER_OF_QUESTIONS_PER_REQUEST, it.value)
                }
                .reduce { x, y ->
                    combineResult(x, y)
                }.toObservable()
        }
    }

    fun onCallQuestion(level: String) =
        Api.triviaService.getQuestions(NUMBER_OF_QUESTIONS_PER_REQUEST, level)


    fun getAllQuestions2(): Observable<State<TriviaResponse>> {
        return wrapperWithState {
            onCallQuestion(QuestionLevel.EASY.value).subscribeOn(Schedulers.io())
                .zipWith(onCallQuestion(QuestionLevel.MEDIUM.value).subscribeOn(Schedulers.io()),
                    BiFunction { firstResponse: Response<TriviaResponse>,
                                 secondResponse: Response<TriviaResponse> ->
                        combineResult(firstResponse, secondResponse)
                    }).zipWith(
                    onCallQuestion(QuestionLevel.HARD.value),
                    BiFunction { firstResponse: Response<TriviaResponse>,
                                 secondResponse: Response<TriviaResponse> ->
                        combineResult(firstResponse, secondResponse)
                    }
                ).toObservable()
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

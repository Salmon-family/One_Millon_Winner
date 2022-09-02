package com.example.onemillonwinner.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.util.NetworkConstants.NUMBER_OF_QUESTIONS_PER_REQUEST
import com.example.onemillonwinner.util.enum.QuestionLevel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response


class Repository {

    fun getAllQuestions(): Observable<State<TriviaResponse>> {

        return wrapperWithState {
            Observable.fromIterable(QuestionLevel.values().asList())
                .flatMapSingle {

                    Api.triviaService.getQuestions(NUMBER_OF_QUESTIONS_PER_REQUEST, it.value)
                        .onErrorResumeNext {
                            null
                        }
                }.onErrorReturn {
                    null
                }
                .reduce { x, y ->
                    combineResult(x, y)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
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

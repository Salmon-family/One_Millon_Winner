package com.example.onemillonwinner.data.network

import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.util.ApiConstants.NUMBER_OF_QUESTIONS_PER_REQUEST
import com.example.onemillonwinner.util.Constants.KEY_SCORE
import com.example.onemillonwinner.util.PreferenceProvider
import com.example.onemillonwinner.util.enumState.QuestionLevel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class Repository {

    fun setBestPrize(currentPrize: Int) {
        val lastPrize = PreferenceProvider().getInt(KEY_SCORE)
        if (lastPrize != null && lastPrize < currentPrize) {
            PreferenceProvider().setInt(KEY_SCORE, currentPrize)
        }
    }

    fun getBestPrize() = PreferenceProvider().getInt(KEY_SCORE)

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

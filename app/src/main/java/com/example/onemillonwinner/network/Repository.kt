package com.example.onemillonwinner.network

import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.util.Constants.KEY_SCORE
import com.example.onemillonwinner.util.NetworkConstants.NUMBER_OF_QUESTIONS_PER_REQUEST
import com.example.onemillonwinner.util.Preference
import com.example.onemillonwinner.util.enum.QuestionLevel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class Repository {

    fun setBestPrize(currentPrize: Int) {
        val lastPrize = Preference.getInt(KEY_SCORE)
        if (lastPrize != null) {
            if (lastPrize < currentPrize) {
                Preference.setInt(currentPrize, KEY_SCORE)
            }
        }
    }

    fun getAllQuestions(): Observable<State<TriviaResponse>> {
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

    fun onCallQuestion(level: String) =
        Api.triviaService.getQuestions(NUMBER_OF_QUESTIONS_PER_REQUEST, level)


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

package com.example.onemillonwinner.network

import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.TriviaResponse
import com.example.onemillonwinner.data.enum.QuestionLevel
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class Repository {

    fun getQuestion(numberOfQuestions: Int, level: QuestionLevel): Single<State<TriviaResponse>> {
        return wrapperWithState {
            Api.triviaService.getQuestions(
                questionNumbers = numberOfQuestions,
                QuestionDifficulty = level.value
            )
        }
    }

    private fun <T> wrapperWithState(function: () -> Single<Response<T>>): Single<State<T>> {
        return function().map {
            if (it.isSuccessful) {
                State.Success(it.body())
            } else {
                State.Failure(it.message())
            }
        }
    }

}

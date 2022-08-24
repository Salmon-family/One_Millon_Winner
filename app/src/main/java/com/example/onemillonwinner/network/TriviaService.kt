package com.example.onemillonwinner.network

import com.example.onemillonwinner.data.TriviaResponse
import com.example.onemillonwinner.data.enum.QuestionLevel
import com.example.onemillonwinner.util.NetworkConstants
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService {
    @GET("api.php")
    fun getQuestions(
        @Query("amount") questionNumbers: Int = 5,
        @Query("difficulty") QuestionDifficulty: String = QuestionLevel.EASY.value,
        @Query("type") questionType: String = NetworkConstants.MULTIPLE_QUESTION_TYPE,
    ): Single<TriviaResponse>
}
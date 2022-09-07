package com.example.onemillonwinner.network

import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.util.ApiConstants
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService {
    @GET("api.php")
    fun getQuestions(
        @Query("amount") questionNumbers: Int,
        @Query("difficulty") QuestionDifficulty: String,
        @Query("type") questionType: String = ApiConstants.MULTIPLE_QUESTION_TYPE,
    ): Single<Response<TriviaResponse>>
}
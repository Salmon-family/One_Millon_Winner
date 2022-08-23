package com.example.onemillonwinner.network

import com.example.onemillonwinner.data.TriviaResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService {
    @GET("api.php")
    fun getQuestions(
        @Query("amount") questionNumbers: Int = 5,
        @Query("type") questionType: String = "multiple",
        @Query("difficulty") QuestionDifficulty: String = "easy"
    ): Single<TriviaResponse>


}
package com.example.onemillonwinner.network

import com.example.onemillonwinner.data.TriviaResponse
import com.example.onemillonwinner.util.NetworkConstants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService {
    @GET("api.php")
    fun getQuestions(
        @Query("amount") questionNumbers: Int = 5,
        @Query("type") questionType: String = NetworkConstants.MULTIPLE_QUESTION_TYPE,
        @Query("difficulty") QuestionDifficulty: String = NetworkConstants.DIFFICULTY_LEVEL_EASY
    ): Single<TriviaResponse>


}
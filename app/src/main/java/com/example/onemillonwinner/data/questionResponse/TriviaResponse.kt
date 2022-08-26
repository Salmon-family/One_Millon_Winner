package com.example.onemillonwinner.data.questionResponse

import com.google.gson.annotations.SerializedName

data class TriviaResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("results")
    val questions: MutableList<Question>
)
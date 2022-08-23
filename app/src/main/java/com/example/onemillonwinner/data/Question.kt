package com.example.onemillonwinner.data

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("correct_answer")
    val correctAnswer: String?,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>?,
    @SerializedName("question")
    val question: String?,

    //Do we really need this?!!
    @SerializedName("difficulty")
    val difficulty: String?
)
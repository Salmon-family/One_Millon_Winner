package com.example.onemillonwinner.data.questionResponse

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("correct_answer")
    val correctAnswer: String?,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>?,
    @SerializedName("question")
    val question: String?,
    @SerializedName("difficulty")
    val difficulty: String?,
    @SerializedName("question_number")
    var questionNumber: Int
)
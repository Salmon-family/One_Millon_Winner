package com.example.onemillonwinner.ui.fragments.game

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.Constants.MAX_NUMBER_OF_QUESTIONS
import kotlin.math.abs


class QuestionLogic {

    private val questions: ArrayList<Question> by lazy { ArrayList() }
    private val currentQuestion: QuestionModel by lazy {
        QuestionModel(
            1,
            questions.first()
        )
    }

    fun setQuestions(newQuestions: List<Question>) {
        questions.addAll(newQuestions)
    }

    fun updateQuestion(): QuestionModel {
        if (!isGameDone()) {
            questions.removeAt(0)
            currentQuestion.number = MAX_NUMBER_OF_QUESTIONS - questions.count()
            currentQuestion.question = questions.first()
        }
        return currentQuestion
    }

    private fun isGameDone() = questions.size == 0

}
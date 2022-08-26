package com.example.onemillonwinner.ui.fragments.game

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.Constants.MAX_NUMBER_OF_QUESTIONS


class QuestionLogic {

    private val questions: ArrayList<Question> by lazy { ArrayList() }

    fun setQuestions(newQuestions: List<Question>) {
        questions.addAll(newQuestions)
    }

    fun updateQuestion(): Question {
        questions.first().questionNumber = MAX_NUMBER_OF_QUESTIONS - questions.lastIndex
        val currentQuestion = questions.first()
        questions.removeAt(0)
        return currentQuestion
    }

    fun isGameDone() = questions.isEmpty()

}
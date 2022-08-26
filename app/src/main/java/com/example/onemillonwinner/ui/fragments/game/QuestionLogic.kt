package com.example.onemillonwinner.ui.fragments.game

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.Constants.MAX_NUMBER_OF_QUESTIONS


class QuestionLogic {

    private val questions: ArrayList<Question> by lazy { ArrayList() }


    fun setQuestions(newQuestions: List<Question>) {
        questions.addAll(newQuestions)
    }

    fun updateQuestion(): Question {
        if (!isGameDone()) {
            questions.removeAt(0)
            questions.first().questionNumber = MAX_NUMBER_OF_QUESTIONS - questions.count()
        }
        return  questions.first()
    }

    private fun isGameDone() = questions.size == 0

}
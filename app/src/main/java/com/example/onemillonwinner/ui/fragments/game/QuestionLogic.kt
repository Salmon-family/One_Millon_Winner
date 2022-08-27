package com.example.onemillonwinner.ui.fragments.game

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.Constants.MAX_NUMBER_OF_QUESTIONS
import java.util.*
import kotlin.collections.ArrayDeque


class QuestionLogic {

    private val questions: Queue<Question> = LinkedList()

    fun setQuestions(newQuestions: List<Question>) {
        questions.addAll(newQuestions)
    }

    private fun updateQuestionNumber() {
        questions.first().questionNumber = MAX_NUMBER_OF_QUESTIONS - questions.size + 1
    }

    fun updateQuestion(): Question {
        val currentQuestion = questions.first()
        updateQuestionNumber()
        questions.poll()
        return currentQuestion
    }

    fun isGameDone() = questions.isEmpty()

}
package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.Constants.MAX_NUMBER_OF_QUESTIONS
import com.example.onemillonwinner.util.enum.SelectAnswer
import java.util.*
import kotlin.collections.ArrayList


class GameQuestionList {
    private val questions = ArrayList<Question>()
    private val currentQuestion = GameQuestion()
    private var currentQuestionSubmitted: Boolean = false

    fun setQuestions(newQuestions: List<Question>) {
        questions.addAll(newQuestions)
    }

    fun setSelectedAnswer(selectAnswerIndex: Int) {
        currentQuestion.selectedAnswerIndex = selectAnswerIndex
    }

    fun isCurrentQuestionSubmitted() = currentQuestionSubmitted

    fun setCurrentQuestionSubmitted(value: Boolean) {
        currentQuestionSubmitted = value
    }

    fun isReadyToSubmit(): Boolean {
        return (currentQuestion.selectedAnswerIndex != -1
                && !currentQuestionSubmitted)
    }


    private fun getQuestionNumber() = MAX_NUMBER_OF_QUESTIONS - questions.indices.last

    fun updateQuestion(): GameQuestion {
        currentQuestion.setQuestion(questions.first())
        currentQuestion.setQuestionNumber(getQuestionNumber())
        questions.removeFirst()
        return currentQuestion
    }

    fun getCurrentQuestion() = currentQuestion

    fun isGameDone() = questions.isEmpty()

}
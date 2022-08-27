package com.example.onemillonwinner.ui.fragments.game

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.Constants.MAX_NUMBER_OF_QUESTIONS
import java.util.*


class GameQuestionList {

    private val questions: Queue<Question> = LinkedList()

    fun setQuestions(newQuestions: List<Question>) {
        questions.addAll(newQuestions)
    }

    private fun getQuestionNumber() = MAX_NUMBER_OF_QUESTIONS - questions.indices.last

    fun updateQuestion(): GameQuestion {
        val currentQuestion = GameQuestion()
        currentQuestion.setQuestion(questions.first())
        currentQuestion.setQuestionNumber(getQuestionNumber())
        questions.poll()
        return currentQuestion
    }

    fun isGameDone() = questions.isEmpty()

}
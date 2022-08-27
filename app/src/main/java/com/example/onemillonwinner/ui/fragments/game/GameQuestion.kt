package com.example.onemillonwinner.ui.fragments.game

import com.example.onemillonwinner.data.questionResponse.Question

class GameQuestion {
    private var questionDescription: String = ""
    private var questionNumber: Int = 0
    private val answers: MutableList<String> = mutableListOf()

    fun setQuestion(question: Question) {
        questionDescription = question.question ?: ""
        question.incorrectAnswers?.let { incorrectAnswers ->
            answers.addAll(incorrectAnswers)
        }
        question.correctAnswer?.let { correctAnswer ->
            answers.add(correctAnswer)
        }
        answers.shuffle()
    }

    fun getQuestion() = questionDescription

    fun setQuestionNumber(number: Int) {
        questionNumber = number
    }

    fun getQuestionNumber() = questionNumber

    fun getAnswers() = answers

}
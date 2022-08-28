package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question

class GameQuestion {
    private var questionDescription: String = ""
    private var questionNumber: Int = 0
    private val answers: MutableList<String> = mutableListOf()
    var selectedAnswerIndex = -1
    var correctAnswerIndex = -1

    fun setQuestion(question: Question) {
        questionDescription = question.question ?: ""
        question.incorrectAnswers?.let { incorrectAnswers ->
            answers.addAll(incorrectAnswers)
        }
        question.correctAnswer?.let { correctAnswer ->
            answers.add(correctAnswer)
        }
        answers.shuffle()
        correctAnswerIndex = answers.indexOf(question.correctAnswer)
    }

    fun getQuestion() = questionDescription

    fun setQuestionNumber(number: Int) {
        questionNumber = number
    }

    fun getQuestionNumber() = questionNumber

    fun getAnswers() = answers

}
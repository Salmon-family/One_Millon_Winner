package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question

class GameQuestion {
    private var questionDescription: String = ""
    private var questionNumber: Int = 0
    private val answers: MutableList<String> = mutableListOf()
    var selectedAnswerIndex = -1
    var correctAnswer: String = ""

    fun setQuestion(question: Question) {
        questionDescription = question.question ?: ""
        answers.clear()
        question.incorrectAnswers?.let { incorrectAnswers ->
            answers.addAll(incorrectAnswers)
        }
        question.correctAnswer?.let { correct ->
            answers.add(correct)
            correctAnswer = correct
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
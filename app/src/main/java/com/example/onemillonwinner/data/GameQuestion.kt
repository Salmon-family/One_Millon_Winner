package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question

class GameQuestion {
    private var questionDescription: String = ""
    private var questionNumber: Int = 0
    private var difficulty: String = ""
    private val answers: MutableList<String> = mutableListOf()
    var correctAnswer: String = ""

    fun setQuestion(question: Question) {
        questionDescription = question.question ?: ""
        difficulty = question.difficulty ?: ""
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

    fun getDifficulty() = difficulty

}
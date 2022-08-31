package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question

class GameQuestion {
    private var questionDescription: String = ""
    private var questionNumber: Int = 0
    private var difficulty: String = ""
    private val answers: MutableList<String> = mutableListOf()
    private var selectedAnswer = -1
    private var correctAnswer: String = ""

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

    fun getAnswers() = answers.toList()

    fun removeWrongAnswer(index: Int): Boolean {
        return if (index in answers.indices
            && answers[index] != correctAnswer
            && answers[index].isNotBlank()
        ) {
            answers[index] = ""
            true
        } else {
            false
        }
    }

    fun getDifficulty() = difficulty

    fun getCorrectAnswer() = correctAnswer

    fun getSelectedAnswer() = selectedAnswer

    fun setSelectedAnswer(index: Int) {
        if (index in 0..3)
            selectedAnswer = index
    }
}
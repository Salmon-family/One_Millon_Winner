package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.extension.htmlText

class GameQuestion(private val question: Question) {

    private var questionNumber: Int = 0
    private var selectedAnswer = -1

    fun getAnswersList(): List<String> {
        return mutableListOf<String>().apply {
            question.incorrectAnswers?.forEach { incorrectAnswer ->
                this.add(incorrectAnswer.htmlText())
            }
            question.correctAnswer?.let { correct ->
                this.add(correct.htmlText())
            }
            this.shuffle()
        }
    }

    fun setQuestionNumber(number: Int) {
        questionNumber = number
    }

    fun getQuestionNumber() = questionNumber

    fun removeTwoWrongAnswersRandomly(): List<String> {
        val answersList = getAnswersList().toMutableList()
        var deletedAnswers = 0
        while (deletedAnswers != 2) {
            val randomNumber = (0..3).random()
            if (answersList[randomNumber] != question.correctAnswer
                && answersList[randomNumber].isNotBlank()) {
                answersList[randomNumber] = ""
                deletedAnswers += 1
            }
        }
        return answersList
    }

    fun getQuestion() = question.question

    fun getDifficulty() = question.difficulty

    fun getCorrectAnswer() = question.correctAnswer

    fun getSelectedAnswer() = selectedAnswer

    fun setSelectedAnswer(index: Int) {
        selectedAnswer = index
    }
}
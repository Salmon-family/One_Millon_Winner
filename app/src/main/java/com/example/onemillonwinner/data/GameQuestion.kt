package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.enumState.ChoicesState
import com.example.onemillonwinner.util.extension.htmlText

class GameQuestion(question: Question) {

    val questionDescription: String
    var questionNumber: Int = 0
    val difficulty: String
    val answers: MutableList<Choice>
    val correctAnswer: String

    init {
        questionDescription = question.question?.htmlText() ?: ""
        difficulty = question.difficulty ?: ""
        answers = mutableListOf()
        this.questionNumber = 0
        question.incorrectAnswers?.forEach { incorrectAnswer ->
            answers.add(Choice(incorrectAnswer.htmlText(), ChoicesState.NOT_SELECTED))
        }
        var correctA = ""
        question.correctAnswer?.let { correct ->
            correctA = correct.htmlText()
            answers.add(Choice(correctA, ChoicesState.NOT_SELECTED))
        }
        correctAnswer = correctA
        answers.shuffle()
    }

    fun updateChoice(choiceNumber: Int): List<Choice> {
        answers.forEachIndexed { index, choice ->
            if (index == choiceNumber) {
                choice.state = ChoicesState.SELECTED
            } else if (choice.state == ChoicesState.DISABLE_SELECTION) {
                choice.state = ChoicesState.DISABLE_SELECTION
            } else {
                choice.state = ChoicesState.NOT_SELECTED
            }
        }
        return answers
    }

    fun isWrongAnswerSelected(): Boolean {
        return answers.indexOfFirst { it.state == ChoicesState.WRONG } != -1
    }

    fun getSelectedAnswer() = answers.indexOfFirst { it.state == ChoicesState.SELECTED }

    fun setSelectedAnswer(index: Int) {
        answers[index].state = ChoicesState.SELECTED
    }

    fun removeAllSelection() {
        answers.forEach {
            it.state = ChoicesState.DISABLE_SELECTION
        }
    }

    fun isAnswerSelected() = answers.any { it.state == ChoicesState.SELECTED }

    fun setCorrectAnswer(): List<Choice> {
        val correctChoiceIndex = answers.indexOfFirst { it.answer == correctAnswer }
        val choiceIndex = answers.indexOfFirst { it.state == ChoicesState.SELECTED }
        answers.forEachIndexed { index, choice ->
            if (correctChoiceIndex == index) {
                choice.state = ChoicesState.CORRECT
            } else if (choiceIndex == index) {
                choice.state = ChoicesState.WRONG
            } else {
                choice.state = ChoicesState.DISABLE_SELECTION
            }
        }
        return answers
    }

    fun checkToRemoveAnswer(index: Int): Boolean {
        return (index in answers.indices
                && answers[index].answer != correctAnswer
                && answers[index].answer.isNotBlank())
    }

    fun deleteTwoWrongAnswersRandomly(): List<Choice> {
        var deletedAnswers = 0
        while (deletedAnswers != 2) {
            val randomNumber = (0..3).random()
            if (checkToRemoveAnswer(randomNumber)) {
                answers[randomNumber] = Choice("", ChoicesState.DISABLE_SELECTION)
                deletedAnswers++
            }
        }
        return answers
    }
}
package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.enumState.ChoicesState
import com.example.onemillonwinner.util.extension.htmlText

class GameQuestion(private val question: Question) {

    var questionNumber: Int = 0

    private val _answers: MutableList<Choice> = mutableListOf()
    val answers: List<Choice>
        get() = _answers


    init {
        questionNumber = 0
        getAnswersList()
    }

    fun getAnswersList() {
        _answers.apply {
            question.incorrectAnswers?.forEach { incorrectAnswer ->
                this.add(Choice(incorrectAnswer.htmlText(), ChoicesState.NOT_SELECTED))
            }
            question.correctAnswer?.let {
                this.add(Choice(it.htmlText(), ChoicesState.NOT_SELECTED))
            }
            shuffle()
        }
    }

    fun updateChoice(choiceNumber: Int): List<Choice> {
        _answers.forEachIndexed { index, choice ->
            if (index == choiceNumber) {
                choice.state = ChoicesState.SELECTED
            } else if (choice.state == ChoicesState.DISABLE_SELECTION) {
                choice.state = ChoicesState.DISABLE_SELECTION
            } else {
                choice.state = ChoicesState.NOT_SELECTED
            }
        }
        return _answers
    }

    fun setCorrectAnswer(): List<Choice> {
        val correctChoiceIndex = _answers.indexOfFirst { it.answer == getCorrectAnswer() }
        val choiceIndex = _answers.indexOfFirst { it.state == ChoicesState.SELECTED }
        _answers.forEachIndexed { index, choice ->
            if (correctChoiceIndex == index) {
                choice.state = ChoicesState.CORRECT
            } else if (choiceIndex == index) {
                choice.state = ChoicesState.WRONG
            } else {
                choice.state = ChoicesState.DISABLE_SELECTION
            }
        }
        return _answers
    }

    fun deleteTwoWrongAnswersRandomly(): List<Choice> {
        var deletedAnswers = 0
        while (deletedAnswers != 2) {
            val randomNumber = (0..3).random()
            if (checkToRemoveAnswer(randomNumber)) {
                _answers[randomNumber] = Choice("", ChoicesState.DISABLE_SELECTION)
                deletedAnswers++
            }
        }
        return _answers
    }

    private fun checkToRemoveAnswer(index: Int): Boolean {
        return (index in _answers.indices
                && _answers[index].answer != getCorrectAnswer()
                && _answers[index].answer.isNotBlank())
    }

    fun removeAllSelection() {
        _answers.forEach {
            it.state = ChoicesState.WRONG
        }
    }

    fun isWrongAnswerSelected(): Boolean {
        return _answers.indexOfFirst { it.state == ChoicesState.WRONG } != -1
    }

    fun getSelectedAnswer() = _answers.indexOfFirst { it.state == ChoicesState.SELECTED }

    fun isAnswerSelected() = _answers.any { it.state == ChoicesState.SELECTED }

    fun getQuestionDescription() = question.question?.htmlText()
    fun getDifficulty() = question.difficulty
    fun getCorrectAnswer() = question.correctAnswer?.htmlText()
}
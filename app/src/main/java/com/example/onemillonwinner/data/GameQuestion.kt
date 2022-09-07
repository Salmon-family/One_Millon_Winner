package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.enumState.ChoicesState
import com.example.onemillonwinner.util.extension.htmlText

class GameQuestion {
    private var questionDescription: String = ""
    var questionNumber: Int = 0
    private var difficulty: String = ""
    private val answers: MutableList<Choice> = mutableListOf()
    private var correctAnswer: String = ""

    fun setQuestion(question: Question, questionNumber: Int) {
        questionDescription = question.question?.htmlText() ?: ""
        difficulty = question.difficulty ?: ""
        answers.clear()
        this.questionNumber = questionNumber
        question.incorrectAnswers?.forEach { incorrectAnswer ->
            answers.add(Choice(incorrectAnswer.htmlText(), ChoicesState.NOT_SELECTED))
        }
        question.correctAnswer?.let { correct ->
            correctAnswer = correct.htmlText()
            answers.add(Choice(correctAnswer, ChoicesState.NOT_SELECTED))
        }
        answers.shuffle()
    }

    fun getQuestion() = questionDescription

    fun getAnswers() = answers.toList()

    fun getDifficulty() = difficulty

    fun getCorrectAnswer() = correctAnswer

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

    fun setCorrectAnswer() {
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
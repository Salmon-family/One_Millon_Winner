package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.extension.htmlText

class GameQuestion {
    private var questionDescription: String = ""
    private var questionNumber: Int = 0
    private var difficulty: String = ""
    private val answers: MutableList<Choice> = mutableListOf()
    private var correctAnswer: String = ""

    fun setQuestion(question: Question) {
        questionDescription = question.question?.htmlText() ?: ""
        difficulty = question.difficulty ?: ""
        answers.clear()
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

    fun setQuestionNumber(number: Int) {
        questionNumber = number
    }

    fun getQuestionNumber() = questionNumber

    fun getAnswers() = answers.toList()

    fun removeWrongAnswer(index: Int): Boolean {
        return if (index in answers.indices
            && answers[index].answer != correctAnswer
            && answers[index].answer.isNotBlank()
        ) {
            answers[index] = Choice("", ChoicesState.DISABLE_SELECTION)
            true
        } else {
            false
        }
    }

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

    fun setCorrectAnswer(){
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

}
package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.Constants.MAX_NUMBER_OF_QUESTIONS
import com.example.onemillonwinner.util.Constants.NUMBER_OF_QUESTIONS_PER_LEVEL
import com.example.onemillonwinner.util.enumState.ChoicesState
import com.example.onemillonwinner.util.enumState.QuestionLevel
import kotlin.collections.ArrayList

class TriviaQuestion {
    private val questions = ArrayList<Question>()
    private val currentQuestion = GameQuestion()
    private val replaceableQuestions: ArrayList<Question> = ArrayList()

    private val prizeList = mapOf(
        1 to 100,
        2 to 200,
        3 to 300,
        4 to 500,
        5 to 1000,
        6 to 2000,
        7 to 4000,
        8 to 8000,
        9 to 16000,
        10 to 32000,
        11 to 64000,
        12 to 125000,
        13 to 250000,
        14 to 500000,
        15 to 1000000
    )

    fun setQuestions(newQuestions: List<Question>) {
        QuestionLevel.values().forEach { level ->
            questions.addAll(newQuestions.filter { it.difficulty == level.value }
                .apply {
                    replaceableQuestions.add(this.last())
                }.take(NUMBER_OF_QUESTIONS_PER_LEVEL))
        }
    }

    fun replaceQuestion(): GameQuestion {
        val replaceableQuestion = replaceableQuestions.first {
            it.difficulty == currentQuestion.getDifficulty()
        }
        currentQuestion.setQuestion(replaceableQuestion, currentQuestion.questionNumber)
        return currentQuestion
    }

    private fun getQuestionNumber() = MAX_NUMBER_OF_QUESTIONS - questions.indices.last

    fun updateQuestion(): GameQuestion {
        currentQuestion.setQuestion(questions.first(), getQuestionNumber())
        questions.removeFirst()
        return currentQuestion
    }

    fun updateAnswersState(): List<Choice> {
        currentQuestion.setCorrectAnswer()
        return currentQuestion.getAnswers()
    }

    fun updateChoice(choiceNumber: Int): List<Choice> {
        currentQuestion.getAnswers().forEachIndexed { index, choice ->
            if (index == choiceNumber) {
                choice.state = ChoicesState.SELECTED
            } else if (choice.state == ChoicesState.DISABLE_SELECTION) {
                choice.state = ChoicesState.DISABLE_SELECTION
            } else {
                choice.state = ChoicesState.NOT_SELECTED
            }
        }
        return currentQuestion.getAnswers()
    }

    fun getCurrentQuestion() = currentQuestion

    fun getAnswersCurrentQuestion() = currentQuestion.getAnswers()

    fun getCorrectAnswer() = currentQuestion.getCorrectAnswer()

    fun deleteTwoWrongAnswersRandomly() = currentQuestion.deleteTwoWrongAnswersRandomly()

    fun isAnswerSelected() = currentQuestion.isAnswerSelected()

    fun isGameOver(): Boolean {
        return questions.isEmpty() || currentQuestion.isWrongAnswerSelected()
    }

    fun getPrize(): Int? {
        val questionNumber = currentQuestion.questionNumber
        return if (currentQuestion.isWrongAnswerSelected()) {
            val result = when (questionNumber) {
                in 6..10 -> prizeList[5]
                in 11..15 -> prizeList[10]
                else -> {
                    0
                }
            }
            result
        } else {
            prizeList[questionNumber]
        }
    }

}
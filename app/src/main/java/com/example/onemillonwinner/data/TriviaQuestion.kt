package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.Constants.MAX_NUMBER_OF_QUESTIONS
import com.example.onemillonwinner.util.Constants.NUMBER_OF_QUESTIONS_PER_LEVEL
import com.example.onemillonwinner.util.enumState.ChoicesState
import com.example.onemillonwinner.util.enumState.QuestionLevel
import kotlin.collections.ArrayList

class TriviaQuestion {
    private val questions = ArrayList<GameQuestion>()
    private lateinit var currentQuestion: GameQuestion
    private val replaceableQuestions: ArrayList<GameQuestion> = ArrayList()

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


    fun setQuestion(questionResponse: List<Question>) {
        for (index in 0 until NUMBER_OF_QUESTIONS_PER_LEVEL) {
            questions.add(GameQuestion(questionResponse[index]))
        }
        replaceableQuestions.add(GameQuestion(questionResponse.last()))
        currentQuestion = questions.first()
    }

    fun replaceQuestion(): GameQuestion {
        val replaceableQuestion = replaceableQuestions.first {
            it.getDifficulty() == currentQuestion.getDifficulty()
        }
        replaceableQuestion.questionNumber = currentQuestion.questionNumber
        currentQuestion = replaceableQuestion
        return currentQuestion
    }

    fun updateQuestion(): GameQuestion {
        currentQuestion = questions.first()
        currentQuestion.questionNumber = getQuestionNumber()
        questions.removeFirst()
        return currentQuestion
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

    fun isGameOver(): Boolean {
        return questions.isEmpty() || currentQuestion.isWrongAnswerSelected()
    }

    fun getQuestionSize() = questions.size

    fun getCurrentQuestion() = currentQuestion

    private fun getQuestionNumber() = MAX_NUMBER_OF_QUESTIONS - questions.indices.last
}
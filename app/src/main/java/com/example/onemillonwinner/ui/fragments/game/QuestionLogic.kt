package com.example.onemillonwinner.ui.fragments.game

import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.enum.QuestionLevel
import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.network.Repository
import io.reactivex.rxjava3.core.Single

const val ONE_ITEM_LEFT = 1

class QuestionLogic {

    private val repository: Repository by lazy { Repository() }
    private var counterLevel = QuestionLevel.values()
    private var questionNumber: Int = 0

    fun updateQuestionsList(questions: List<Question>): Pair<List<Question>, Boolean> {
        questionNumber++
        return Pair(questions.drop(1), isNeedToUpdateQuestions(questions))
    }

    fun getQuestionNumber(): Int {
        return questionNumber
    }

    private fun isGameDone(): Boolean = counterLevel.isEmpty()

    private fun getLevel(): QuestionLevel {
        val level = counterLevel.first()
        counterLevel = counterLevel.drop(1).toTypedArray()
        return level
    }

    fun updateQuestionsLevel(): Single<State<TriviaResponse>>? {
        return if (isGameDone()) {
            null
        } else {
            getTriviaQuestions(5, getLevel())
        }
    }

    private fun isNeedToUpdateQuestions(questions: List<Question>) = questions.size == ONE_ITEM_LEFT

    private fun getTriviaQuestions(
        numberOfQuestions: Int,
        level: QuestionLevel
    ): Single<State<TriviaResponse>> {
        return repository.getQuestion(numberOfQuestions, level)
    }
}
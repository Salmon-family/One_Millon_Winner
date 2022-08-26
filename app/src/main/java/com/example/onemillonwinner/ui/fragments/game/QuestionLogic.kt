package com.example.onemillonwinner.ui.fragments.game

import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.enum.QuestionLevel
import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.network.Repository
import io.reactivex.rxjava3.core.Single

const val ONE_ITEM_LEFT = 1

enum class GameState {
    DONE,
    LEVEL_END,
    IN_PROGRESS
}

class QuestionLogic {

    private var counterLevel = QuestionLevel.values()
    private var questionNumber: Int = 1
    private val questions: ArrayList<Question> by lazy { ArrayList() }

    fun setQuestions(newQuestions: List<Question>) {
        questions.clear()
        questions.addAll(newQuestions)
    }

    fun getQuestion() = questions.first().question

    fun updateQuestionsList(): GameState {
        questionNumber++
        questions.removeAt(0)

        return if (isNeedToUpdateQuestions()) {
            GameState.LEVEL_END
        } else if (isGameDone()) {
            GameState.DONE
        } else {
            GameState.IN_PROGRESS
        }
    }

    fun getQuestionNumber() = questionNumber

    private fun isGameDone(): Boolean = counterLevel.isEmpty()

    private fun getLevel(): QuestionLevel {
        val level = counterLevel.first()
        counterLevel = counterLevel.drop(1).toTypedArray()
        return level
    }

    fun updateQuestionsLevel(
        repository: Repository
    ): Single<State<TriviaResponse>> =
        getTriviaQuestions(5, getLevel(), repository)


    private fun isNeedToUpdateQuestions() = questions.size <= ONE_ITEM_LEFT

    private fun getTriviaQuestions(
        numberOfQuestions: Int,
        level: QuestionLevel,
        repository: Repository
    ): Single<State<TriviaResponse>> {
        return repository.getQuestion(numberOfQuestions, level)
    }
}
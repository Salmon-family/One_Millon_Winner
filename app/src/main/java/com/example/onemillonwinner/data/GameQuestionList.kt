package com.example.onemillonwinner.data

import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.util.Constants.MAX_NUMBER_OF_QUESTIONS
import com.example.onemillonwinner.util.Constants.NUMBER_OF_QUESTIONS_PER_LEVEL
import com.example.onemillonwinner.util.enum.QuestionLevel
import kotlin.collections.ArrayList


class GameQuestionList {
    private val questions = ArrayList<Question>()
    private val currentQuestion = GameQuestion()
    private val replaceableQuestions: ArrayList<Question> = ArrayList()

    fun deleteTwoWrongAnswersRandomly(): GameQuestion {
        var deletedAnswers = 0
        while (deletedAnswers != 2) {
            val randomNumber = (0..3).random()
            if (
                currentQuestion.getAnswers()[randomNumber] != currentQuestion.correctAnswer &&
                currentQuestion.getAnswers()[randomNumber] != ""
            ) {
                currentQuestion.getAnswers()[randomNumber] = ""
                deletedAnswers += 1
            }
        }
        return currentQuestion
    }

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
        currentQuestion.setQuestion(replaceableQuestion)
        return currentQuestion
    }

    private fun getQuestionNumber() = MAX_NUMBER_OF_QUESTIONS - questions.indices.last

    fun updateQuestion(): GameQuestion {
        currentQuestion.setQuestion(questions.first())
        currentQuestion.setQuestionNumber(getQuestionNumber())
        questions.removeFirst()
        return currentQuestion
    }

    fun getCurrentQuestion() = currentQuestion

    fun isGameDone() = questions.isEmpty()

    fun isReplaceQuestionUsed() = replaceableQuestions.isEmpty()

}
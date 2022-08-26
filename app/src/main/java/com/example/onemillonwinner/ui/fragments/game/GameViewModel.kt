package com.example.onemillonwinner.ui.fragments.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.data.questionResponse.Question

class GameViewModel : ViewModel() {
    private val questionLogic: QuestionLogic by lazy { QuestionLogic() }

    private val _questionsStateLiveData = MutableLiveData<State<TriviaResponse>>()
    val state: LiveData<State<TriviaResponse>>
        get() = _questionsStateLiveData

    private val _questionsLevelLiveData = MutableLiveData<List<Question>?>()
    val questions: LiveData<List<Question>?>
        get() = _questionsLevelLiveData

    init {
        _questionsStateLiveData.postValue(State.Loading)
        questionLogic.updateQuestionsLevel()
            ?.subscribe(::onSuccessUpdateQuestion, ::onErrorUpdateQuestion)
    }

    private fun onSuccessUpdateQuestion(state: State<TriviaResponse>) {
        _questionsStateLiveData.postValue(state)
        when (state) {
            is State.Success -> {
                state.toData()?.let {
                    _questionsLevelLiveData.postValue(it.questions)
                }
            }
            is State.Failure -> {

            }
            is State.Loading -> {

            }
        }

    }

    private fun onErrorUpdateQuestion(throwable: Throwable) {

    }

    fun getNextQuestion() {
        _questionsLevelLiveData.value?.let { questions ->
            val game = questionLogic.updateQuestionsList(questions)
            _questionsLevelLiveData.postValue(game.first)
            if (game.second) {
                questionLogic.updateQuestionsLevel()
                    ?.subscribe(::onSuccessUpdateQuestion, ::onErrorUpdateQuestion) ?: gameDone()
            }
        }
    }

    fun updateQuestionNumber(): Int {
        return questionLogic.getQuestionNumber()
    }

    private fun gameDone() {

    }

}


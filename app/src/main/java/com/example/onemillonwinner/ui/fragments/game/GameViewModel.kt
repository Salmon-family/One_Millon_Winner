package com.example.onemillonwinner.ui.fragments.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.network.Repository

class GameViewModel : ViewModel() {
    private val questionLogic: QuestionLogic by lazy { QuestionLogic() }
    private val repository: Repository by lazy { Repository() }

    private val _questionsStateLiveData = MutableLiveData<State<TriviaResponse>>()
    val state: LiveData<State<TriviaResponse>>
        get() = _questionsStateLiveData

    private val _questionsLevelLiveData = MutableLiveData<QuestionModel>()
    val questions: LiveData<QuestionModel>
        get() = _questionsLevelLiveData

    init {
        _questionsStateLiveData.postValue(State.Loading)
        questionLogic.updateQuestionsLevel(repository)
            .subscribe(::onSuccessUpdateQuestion, ::onErrorUpdateQuestion)
    }

    private fun onSuccessUpdateQuestion(state: State<TriviaResponse>) {
        _questionsStateLiveData.postValue(state)
        when (state) {
            is State.Success -> {
                state.toData()?.let {
                    questionLogic.setQuestions(it.questions)
                    _questionsLevelLiveData.postValue(questionLogic.setCurrentQuestion())
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
        when (questionLogic.updateQuestionsList()) {
            GameState.LEVEL_END -> {
                questionLogic.updateQuestionsLevel(repository)
                    .subscribe(::onSuccessUpdateQuestion, ::onErrorUpdateQuestion)
            }
            GameState.IN_PROGRESS -> {
                _questionsLevelLiveData.postValue(questionLogic.setCurrentQuestion())
            }
            GameState.DONE -> {

            }
        }
    }

}


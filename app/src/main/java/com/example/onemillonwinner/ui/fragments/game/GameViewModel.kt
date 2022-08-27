package com.example.onemillonwinner.ui.fragments.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.Question
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.network.Repository

class GameViewModel : ViewModel() {
    private val repository = Repository()
    var isChangeQuestion = MutableLiveData(false)
    var isDeleteHalfOfAnswers = MutableLiveData(false)
    var isHelpByFriends = MutableLiveData(false)

    private val questionLogic: QuestionLogic by lazy { QuestionLogic() }
    private val repository: Repository by lazy { Repository() }

    private val _gameStateLiveData = MutableLiveData<State<TriviaResponse>>()
    val state: LiveData<State<TriviaResponse>>
        get() = _gameStateLiveData

    private val _questionLiveData = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _questionLiveData

    init {
        _gameStateLiveData.postValue(State.Loading)
        repository.getAllQuestions().subscribe(::onSuccessUpdateQuestion, ::onErrorUpdateQuestion)
    }

    private fun onSuccessUpdateQuestion(state: State<TriviaResponse>) {
        _gameStateLiveData.postValue(state)
        state.toData()?.let {
            questionLogic.setQuestions(it.questions)
            getNextQuestion()
        }
    }

    private fun onErrorUpdateQuestion(throwable: Throwable) {

    }

    fun getNextQuestion() {
        if (!questionLogic.isGameDone()) {
            _questionLiveData.postValue(questionLogic.updateQuestion())
        }
    }
    fun changeQuestion(){
        isChangeQuestion.value = true
    }

    fun deleteHalfOfAnswers(){
        isDeleteHalfOfAnswers.value = true
    }

    fun helpByFriends(){
        isHelpByFriends.value = true
    }

}


package com.example.onemillonwinner.ui.fragments.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.TriviaResponse
import com.example.onemillonwinner.data.enum.QuestionLevel
import com.example.onemillonwinner.network.Repository

class GameViewModel : ViewModel() {
    private val repository = Repository()

    private val _questionsLiveData = MutableLiveData<State<TriviaResponse>>()
    val questions: LiveData<State<TriviaResponse>>
        get() = _questionsLiveData

    fun getTriviaQuestions() {
        _questionsLiveData.postValue(State.Loading)

        repository.getQuestion(5, QuestionLevel.EASY).subscribe(
            {
                _questionsLiveData.postValue(it)
                Log.v("testApi", it.toString())
            }, {
                Log.v("testApi", it.message.toString())
            }
        )
    }

}


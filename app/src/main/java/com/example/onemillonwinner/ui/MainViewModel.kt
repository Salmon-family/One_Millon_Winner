package com.example.onemillonwinner.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.NetworkStatus
import com.example.onemillonwinner.data.TriviaResponse
import com.example.onemillonwinner.network.Repository

class MainViewModel : ViewModel() {
    private val _repository = Repository()

//    private val _questions = MutableLiveData<NetworkStatus<TriviaResponse>>()
    val questions: LiveData<NetworkStatus<TriviaResponse>>
        get() = _repository.questions

    init {
        _repository.getQuestions(5)
    }

}


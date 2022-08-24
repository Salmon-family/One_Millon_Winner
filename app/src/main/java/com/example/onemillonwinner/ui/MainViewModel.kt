package com.example.onemillonwinner.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.NetworkStatus
import com.example.onemillonwinner.data.TriviaResponse
import com.example.onemillonwinner.network.Repository

class MainViewModel : ViewModel() {
    private val repository = Repository()
    private val _questions = MutableLiveData<TriviaResponse>()
    val questions: LiveData<TriviaResponse>
        get() = _questions

    init {
        repository.getQuestion(5).subscribe(
            {
            it.toData()
                _questions.postValue(it.toData())
            },{

            }
        )
    }

}


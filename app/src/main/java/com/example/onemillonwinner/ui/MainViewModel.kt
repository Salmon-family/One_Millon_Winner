package com.example.onemillonwinner.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.TriviaResponse
import com.example.onemillonwinner.network.Repository

class MainViewModel : ViewModel() {

    private val repository = Repository()
    private val _questions = MutableLiveData<State<TriviaResponse>>()
    val questions: LiveData<State<TriviaResponse>>
        get() = _questions

   fun getTriviaQuestions() {
       _questions.postValue(State.Loading)
       repository.getQuestion(5).subscribe(
           {
              _questions.postValue(it)
               Log.v("testApi", it.toString())
           },{
               Log.v("testApi", it.message.toString())
           }
       )
   }

}


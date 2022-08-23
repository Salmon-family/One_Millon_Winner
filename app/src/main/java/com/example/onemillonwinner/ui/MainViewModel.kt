package com.example.onemillonwinner.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.data.TriviaResponse
import com.example.onemillonwinner.network.Network
import com.example.onemillonwinner.network.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val _repository = MutableLiveData<Repository>()

    private val _questions = MutableLiveData<TriviaResponse>()
    val questions: LiveData<TriviaResponse>
        get() = _questions


    fun getEasyQuestion() {
        Network.triviaService.getQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _questions.postValue(it)
                },
                {

                }
            )
    }

}
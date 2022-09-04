package com.example.onemillonwinner.ui.fragments.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.network.Repository
import com.example.onemillonwinner.ui.base.BaseViewModel

class ResultViewModel : BaseViewModel() {

    private val repository: Repository by lazy { Repository() }

    private val _prize = MutableLiveData(0)
    val prize: LiveData<Int>
        get() = _prize

    private val _game = MutableLiveData<Boolean>()
    val game: LiveData<Boolean>
        get() = _game

    private val _home = MutableLiveData<Boolean>()
    val home: LiveData<Boolean>
        get() = _home


    fun setPrize(prize: Int) {
        _prize.postValue(prize)
        repository.setBestPrize(prize)
    }

    fun playAgain() {
        _game.postValue(true)
    }

    fun backToTheHome() {
        _home.postValue(true)
    }

}
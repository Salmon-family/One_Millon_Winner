package com.example.onemillonwinner.ui.fragments.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.network.Repository
import com.example.onemillonwinner.ui.base.BaseViewModel

class ResultViewModel : BaseViewModel() {

    private val repository = Repository()

    private val _prize = MutableLiveData(0)
    val prize: LiveData<Int>
        get() = _prize

    private val _isHasPrize = MutableLiveData<Boolean>()
    val isHasPrize: LiveData<Boolean>
        get() = _isHasPrize

    private val _navigateGame = MutableLiveData<Boolean>()
    val navigateGame: LiveData<Boolean>
        get() = _navigateGame

    private val _navigateHome = MutableLiveData<Boolean>()
    val navigateHome: LiveData<Boolean>
        get() = _navigateHome

    init {
        isOwning()
    }

    fun setPrize(prize: Int) {
        _prize.postValue(prize)
        repository.setBestPrize(prize)
    }

    fun playAgain() {
        _navigateGame.postValue(true)
    }

    fun backToTheHome() {
        _navigateHome.postValue(true)
    }

    fun isOwning() {
        _isHasPrize.value = prize.value != 0
    }

}
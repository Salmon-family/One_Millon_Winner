package com.example.onemillonwinner.ui.fragments.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.network.Repository
import com.example.onemillonwinner.ui.base.BaseViewModel

class ResultViewModel : BaseViewModel() {

    private val repository: Repository by lazy { Repository() }
    val prize = MutableLiveData<Int>()


    init {

        setPrize()
    }

    fun setPrize(){
        prize.value?.let { repository.setBestPrize(it) }
    }
}
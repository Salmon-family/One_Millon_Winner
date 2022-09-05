package com.example.onemillonwinner.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.network.Repository
import com.example.onemillonwinner.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    private val repository = Repository()
    private val _bestPrize = MutableLiveData<Int>()
    val bestPrize: LiveData<Int>
        get() = _bestPrize

    init {
        _bestPrize.value = repository.getBestPrize()
    }
}
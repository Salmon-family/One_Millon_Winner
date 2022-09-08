package com.example.onemillonwinner.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.data.network.Repository
import com.example.onemillonwinner.ui.base.BaseViewModel
import com.example.onemillonwinner.util.Event

class HomeViewModel : BaseViewModel() {
    private val repository = Repository()

    private val _navigateToGameFragment = MutableLiveData(Event(false))
    val navigateToGameFragment: LiveData<Event<Boolean>>
        get() = _navigateToGameFragment

    private val _bestPrize = MutableLiveData<Int>(repository.getBestPrize())
    val bestPrize: LiveData<Int>
        get() = _bestPrize


    fun navigateToGameFragment() {
        _navigateToGameFragment.postValue(Event(true))
    }
}
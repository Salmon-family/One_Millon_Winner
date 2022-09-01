package com.example.onemillonwinner.ui.fragments.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.ui.base.BaseViewModel
import com.example.onemillonwinner.util.Constants
import com.example.onemillonwinner.util.Preference

class HomeViewModel : BaseViewModel() {

    private val _bestPrize = MutableLiveData<Int>()
    val bestPrize: LiveData<Int>
        get() = _bestPrize

    init {
        _bestPrize.postValue(Preference.getInt(Constants.KEY_SCORE))
    }
}
package com.example.onemillonwinner.ui.fragments.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {

    private val _prize = MutableLiveData<String>()
    val prize: LiveData<String>
        get() = _prize


}
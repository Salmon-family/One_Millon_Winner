package com.example.onemillonwinner.ui.fragments.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.ui.base.BaseViewModel

//Do we really need this ?!!
class ResultViewModel : BaseViewModel() {

    private val _prize = MutableLiveData<String>()
    val prize: LiveData<String>
        get() = _prize


}
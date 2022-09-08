package com.example.onemillonwinner.ui.fragments.game.exitDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.ui.base.BaseViewModel

class ExitViewModel : BaseViewModel() {

    private val _isExitGame = MutableLiveData(false)
    val isExitGame : LiveData<Boolean>
        get() = _isExitGame

    private val _isDialogClose = MutableLiveData(false)
    val isDialogClose: LiveData<Boolean>
        get() = _isDialogClose

    fun exitFromGame() {
        _isExitGame.postValue(true)
    }
    fun closeDialog(){
        _isDialogClose.postValue(true)
    }

}
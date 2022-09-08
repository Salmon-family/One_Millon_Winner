package com.example.onemillonwinner.ui.game.exitDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.ui.base.BaseViewModel
import com.example.onemillonwinner.util.Event

class ExitViewModel : BaseViewModel() {

    private val _isExitGame = MutableLiveData(Event(false))
    val isExitGame : LiveData<Event<Boolean>>
        get() = _isExitGame

    private val _isDialogClose = MutableLiveData(Event(false))
    val isDialogClose: LiveData<Event<Boolean>>
        get() = _isDialogClose

    fun exitFromGame() {
        _isExitGame.postValue(Event(true))
    }
    fun closeDialog(){
        _isDialogClose.postValue(Event(true))
    }

}
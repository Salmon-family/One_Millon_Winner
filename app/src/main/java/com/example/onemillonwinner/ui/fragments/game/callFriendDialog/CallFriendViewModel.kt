package com.example.onemillonwinner.ui.fragments.game.callFriendDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onemillonwinner.ui.base.BaseViewModel
import com.example.onemillonwinner.util.Event

class CallFriendViewModel: BaseViewModel() {

    private val _correctAnswer = MutableLiveData<String>()
    val correctAnswer : LiveData<String>
    get() = _correctAnswer

    private val _isDialogClose = MutableLiveData(Event(false))
    val isDialogClose : LiveData<Event<Boolean>>
    get() = _isDialogClose

    fun setCorrectAnswer(answer : String){
        _correctAnswer.postValue(answer)
    }

    fun closeDialog(){
        _isDialogClose.postValue(Event(true))
    }
}
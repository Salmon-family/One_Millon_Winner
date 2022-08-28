package com.example.onemillonwinner.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onemillonwinner.util.Event
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class HomeViewModel: ViewModel() {

    private val _naveToGameFragment = MutableLiveData<Event<Boolean>>()
    val naveToGameFragment : LiveData<Event<Boolean>>
        get() = _naveToGameFragment

    fun navigateToGameFragmentAfterThreeSecond(){
        Observable.timer(3, TimeUnit.SECONDS).subscribe {
            _naveToGameFragment.postValue(Event(true))
        }
    }
}
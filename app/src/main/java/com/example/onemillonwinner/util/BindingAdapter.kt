package com.example.onemillonwinner.util

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.onemillonwinner.R

@BindingAdapter(value = ["disableButton"])
fun disableButton(view: View, value: Boolean?){
    if(value == true){
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red_200))
        view.isClickable = false
    }else{
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.blue_200))
    }
}
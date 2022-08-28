package com.example.onemillonwinner.ui.fragments.game

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit


@BindingAdapter(value = ["app:timerText"])
fun timerText(textView: TextView, timeMillisecond: Long) {
    object : CountDownTimer(timeMillisecond, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            textView.text = "${millisUntilFinished / 1000}"
            // logic to set the EditText could go here
        }

        override fun onFinish() {
            //do something when the countdown is complete
        }
    }.start()
}

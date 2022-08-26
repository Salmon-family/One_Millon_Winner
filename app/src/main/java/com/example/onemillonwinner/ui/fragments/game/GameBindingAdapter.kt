package com.example.onemillonwinner.ui.fragments.game

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter

@SuppressLint("ObjectAnimatorBinding")
@BindingAdapter(value = ["app:timerProgressBar"])
fun timerProgressBar(view: ProgressBar, timeMill: Long) {
    val animation = ObjectAnimator.ofInt(view, "progress", 100, 0)
    animation.duration = timeMill
    animation.interpolator = DecelerateInterpolator()
    animation.addListener(object : Animator.AnimatorListener {

        override fun onAnimationStart(animator: Animator) {}

        override fun onAnimationEnd(animator: Animator) {
            //do something when the countdown is complete
        }

        override fun onAnimationCancel(animator: Animator) {}

        override fun onAnimationRepeat(animator: Animator) {}
    })
    animation.start()
}

@BindingAdapter(value = ["app:timerText"])
fun timerText(textView: TextView, timeMill: Long) {
    object : CountDownTimer(timeMill, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            textView.text = "${millisUntilFinished / 1000}"
            // logic to set the EditText could go here
        }

        override fun onFinish() {
            //do something when the countdown is complete
        }
    }.start()
}

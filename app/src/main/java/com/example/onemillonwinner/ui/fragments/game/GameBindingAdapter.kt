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

@SuppressLint("ObjectAnimatorBinding")
@BindingAdapter(value = ["app:timerProgressBar"])
fun timerProgressBar(progressBar: ProgressBar, timeMillisecond: Long) {
    val viewModel = GameViewModel()

    val animation = ObjectAnimator.ofInt(progressBar, "progress", 100, 0)
    animation.duration = timeMillisecond
    animation.interpolator = DecelerateInterpolator()
    animation.addListener(object : Animator.AnimatorListener {

        override fun onAnimationStart(animator: Animator) {
            Log.i("questionTimeIsOver", viewModel.questionTime.value.toString())
        }

        override fun onAnimationEnd(animator: Animator) {
            viewModel.questionTimeIsOver()
            Log.i("questionTimeIsOver", viewModel.questionTime.value.toString())
        }

        override fun onAnimationCancel(animator: Animator) {
        }

        override fun onAnimationRepeat(animator: Animator) {

        }
    })
    animation.start()
}

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

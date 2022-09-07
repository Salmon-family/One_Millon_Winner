package com.example.onemillonwinner.util

import android.content.res.Configuration
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.core.view.children
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameQuestion
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.util.enumState.ChoicesState
import com.example.onemillonwinner.data.StateNetwork
import com.google.android.material.chip.Chip


@BindingAdapter("app:isLoading")
fun <T> showWhenLoading(view: View, state: State<T>?) {
    view.isVisible = state is State.Loading
}

@BindingAdapter("app:isSuccess")
fun <T> showWhenSuccess(view: View, state: State<T>?) {
    view.isVisible = state is State.Success
}

@BindingAdapter("app:isFail")
fun <T> showWhenFail(view: View, state: State<T>?) {
    view.isVisible = state is State.Failure
}


@BindingAdapter("app:buttonUpdateText")
fun updateTextButton(submitButton: Button, state: QuestionState?) {
    state?.let {
        when (state) {
            QuestionState.QUESTION_SUBMITTED -> {
                submitButton.text = submitButton.context.resources.getText(R.string.next_question)
                submitButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_right, 0)
            }
            QuestionState.WRONG_ANSWER_SUBMITTED -> {
                submitButton.text = submitButton.context.resources.getText(R.string.game_over)
                submitButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
            else -> {
                submitButton.text = submitButton.context.resources.getText(R.string.submit)
                submitButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
        }
    }
}


@BindingAdapter("app:choiceState")
fun updateChip(chip: Chip, state: ChoicesState?) {
    state?.let {
        when (it) {
            ChoicesState.WRONG -> {
                chip.isEnabled = false
                chip.setChipBackgroundColorResource(R.color.state_wrong_answer)
            }
            ChoicesState.CORRECT -> {
                chip.isEnabled = false
                chip.setChipBackgroundColorResource(R.color.state_success_answer)
            }
            ChoicesState.DISABLE_SELECTION -> {
                chip.isEnabled = false
                chip.setChipBackgroundColorResource(R.color.state_answer_default)
            }
            ChoicesState.SELECTED -> {
                chip.isEnabled = true
                chip.setChipBackgroundColorResource(R.color.state_select_answer)
            }
            ChoicesState.NOT_SELECTED -> {
                chip.isEnabled = true
                chip.setChipBackgroundColorResource(R.color.state_answer_default)
            }
        }
    }
}

@BindingAdapter("app:prizeLottie")
fun setPrizeLottie(view: LottieAnimationView, isPrize: Boolean) {
    if (isPrize) {
        view.setAnimation(R.raw.lottie_congratulation)
    } else {
        view.setAnimation(R.raw.lottie_loss)
    }
}

@BindingAdapter("app:prizeText")
fun setPrizeText(view: TextView, isPrize: Boolean) {
    if (isPrize) {
        view.setText(R.string.text_congratulation)
    } else {
        view.setText(R.string.better_luck_next_time)
    }
}

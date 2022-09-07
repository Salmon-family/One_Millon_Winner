package com.example.onemillonwinner.util

import android.content.res.Configuration
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.*
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.util.enumState.ChoicesState
import com.example.onemillonwinner.util.enumState.QuestionState
import com.google.android.material.chip.Chip

@BindingAdapter(value = ["app:hide"])
fun hideScoreOfFirstLogin(view: View, value: Int?) {
    if (value != -1) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter(value = ["app:disableButton"])
fun disableButton(view: View, value: Boolean?) {
    if (value == true) {
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.state_disable_button))
        view.isClickable = false
    } else {
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.secondary_color))
    }
}

@BindingAdapter("app:isLoading")
fun showWhenLoading(view: View, state: State<TriviaResponse>?) {
    if (state == State.Loading) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("app:isSuccess")
fun showWhenSuccess(view: View, state: State<TriviaResponse>?) {
    if (state != State.Loading && state !is State.Failure) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("app:isFail")
fun showWhenFail(view: View, state: State<TriviaResponse>?) {
    if (state is State.Failure) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("app:buttonUpdateText")
fun updateTextButton(submitButton: Button, state: QuestionState?) {
    state?.let {
        when (state) {
            QuestionState.QUESTION_SUBMITTED -> {
                submitButton.text = submitButton.context.resources.getText(R.string.next_question)
                submitButton.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_right,
                    0
                )
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
fun setPrizeLottie(view: LottieAnimationView, prize: Int) {
    if (prize == 0) {
        view.setAnimation(R.raw.lottie_loss)
    } else {
        view.setAnimation(R.raw.lottie_congratulation)
    }
}

@BindingAdapter("app:prizeText")
fun setPrizeText(view: TextView, prize: Int) {
    if (prize == 0) {
        view.setText(R.string.better_luck_next_time)
    } else {
        view.setText(R.string.text_congratulation)
    }
}

@BindingAdapter("app:setAnimationLottie")
fun setAnimation(view: LottieAnimationView, id: Int?) {
    when (getThemeMode(view)) {
        Configuration.UI_MODE_NIGHT_YES -> view.setAnimation(R.raw.lottie_dark_loading)
        Configuration.UI_MODE_NIGHT_NO -> view.setAnimation(R.raw.lottie_light_loading)
        Configuration.UI_MODE_NIGHT_UNDEFINED -> view.setAnimation(R.raw.lottie_light_loading)
    }
}

private fun getThemeMode(view: View): Int {
    return view.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
}

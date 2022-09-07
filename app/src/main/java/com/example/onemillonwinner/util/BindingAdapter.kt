package com.example.onemillonwinner.util

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import androidx.core.view.children
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameQuestion
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.StateNetwork
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


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
fun updateTextButton(submitButton: Button, state: GameState?) {
    state?.let {
        when (state) {
            GameState.QUESTION_SUBMITTED -> {
                submitButton.text = submitButton.context.resources.getText(R.string.next_question)
                submitButton.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_right,
                    0
                )
            }
            GameState.WRONG_ANSWER_SUBMITTED -> {
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

@BindingAdapter(value = ["app:changeColorSelection", "app:gameState"])
fun updateChip(chipGroup: ChipGroup, question: GameQuestion?, gameState: GameState?) {
    val selectedID = chipGroup.checkedChipId
    question?.let {
        if (gameState == GameState.QUESTION_SUBMITTED ||
            gameState == GameState.WRONG_ANSWER_SUBMITTED
        ) {
            chipGroup.children.forEach { chip ->
                chip as Chip
                chip.isEnabled = false
                if (chip.text.toString() == question.getCorrectAnswer()) {
                    chip.setChipBackgroundColorResource(R.color.state_success_answer)
                }
                if (selectedID == chip.id && chip.text.toString() != question.getCorrectAnswer()) {
                    chip.setChipBackgroundColorResource(R.color.state_wrong_answer)
                }
            }
        } else if (gameState == GameState.QUESTION_START) {
            chipGroup.clearCheck()
            chipGroup.children.forEachIndexed { index, chip ->
                chip as Chip
                chip.isEnabled = question.getAnswers()[index] != ""
                chip.chipBackgroundColor =
                    AppCompatResources.getColorStateList(chip.context, R.color.selected_chip)
            }
        } else if (gameState == GameState.ANSWER_SELECTED) {
            chipGroup.children.forEachIndexed { index, chip ->
                if (selectedID == chip.id) {
                    question.setSelectedAnswer(index)
                }
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


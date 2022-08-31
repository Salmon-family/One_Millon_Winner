package com.example.onemillonwinner.util

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.core.view.children
import com.airbnb.lottie.LottieAnimationView
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameQuestion
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.util.extension.htmlText
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

@BindingAdapter(value = ["app:disableButton"])
fun disableButton(view: View, value: Boolean?) {
    if (value == true) {
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red_200))
        view.isClickable = false
    } else {
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.blue_200))
    }
}

@BindingAdapter("app:isLoading")
fun showWhenLoading(view: View, state: GameState?) {
    if (state == GameState.Loading) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("app:isSuccess")
fun showWhenSuccess(view: View, state: GameState?) {
    if (state != GameState.Loading && state != GameState.Failure) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("app:isFail")
fun showWhenFail(view: View, state: State<GameState>?) {
    if (state is State.Failure) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
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
        when (gameState) {
            GameState.QUESTION_SUBMITTED,
            GameState.WRONG_ANSWER_SUBMITTED -> {
                chipGroup.children.forEachIndexed { index, chip ->
                    chip as Chip
                    chip.isEnabled = false
                    if (chip.text.toString() == question.getCorrectAnswer().htmlText()) {
                        chip.setChipBackgroundColorResource(R.color.teal_200)
                    }
                    if (selectedID == chip.id && chip.text.toString() != question.getCorrectAnswer()) {
                        chip.setChipBackgroundColorResource(R.color.red_200)
                    }
                }
            }
            GameState.QUESTION_START -> {
                chipGroup.clearCheck()
                chipGroup.children.forEachIndexed { index, chip ->
                    chip as Chip
                    chip.isEnabled = question.getAnswers()[index] != ""
                    chip.chipBackgroundColor =
                        AppCompatResources.getColorStateList(chip.context, R.color.selected_chip)
                }
            }
            GameState.ANSWER_SELECTED -> {
                chipGroup.children.forEachIndexed { index, chip ->
                    if (selectedID == chip.id) {
                        question.setSelectedAnswer(index)
                    }
                }
            }
            else -> {

            }
        }
    }
}

@BindingAdapter("app:formatTextFromHtml")
fun formatTextFromHtml(view: TextView, text: String?) {
    text?.let {
        view.text = it.htmlText()
    }
}

@BindingAdapter("app:prizeLottie")
fun setPrizeLottie(view: LottieAnimationView, prize: String) {
    if (prize == "$0") {
        view.setAnimation(R.raw.you_loss)
    } else {
        view.setAnimation(R.raw.lottie_congratulation)
    }
}

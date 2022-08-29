package com.example.onemillonwinner.util

import android.view.View
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.*
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameQuestion
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.util.enum.SelectAnswer
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

@BindingAdapter(value = ["app:rawAnswersColor"])
fun rawAnswersColor(view: View, selectedAnswer: SelectAnswer?) {
    when (selectedAnswer) {
        SelectAnswer.UNSELECTED_ANSWER -> view.background = ContextCompat
            .getDrawable(view.context, R.drawable.raw_unselected_answers_shape)
        SelectAnswer.CORRECT_ANSWER -> view.background = ContextCompat
            .getDrawable(view.context, R.drawable.raw_correct_answers_shape)
        SelectAnswer.INCORRECT_ANSWER -> view.background = ContextCompat
            .getDrawable(view.context, R.drawable.raw_incorrect_answers_shape)
        else -> view.background = ContextCompat
            .getDrawable(view.context, R.drawable.raw_unselected_answers_shape)
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
        if (state == GameState.QUESTION_SUBMITTED) {
            submitButton.text = submitButton.context.resources.getText(R.string.next_question)
            submitButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_right, 0)
        } else {
            submitButton.text = submitButton.context.resources.getText(R.string.submit)
            submitButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
    }
}

@BindingAdapter("app:changeColorSelection")
fun updateColorSelectedAnswer(chipGroup: ChipGroup, question: GameQuestion?) {
    val selectedID = chipGroup.checkedChipId
    chipGroup.clearCheck()
    if (selectedID == -1) {
        chipGroup.children.forEach { chip ->
            chip as Chip
            chip.isEnabled = true
            chip.chipBackgroundColor =
                AppCompatResources.getColorStateList(chip.context, R.color.selected_chip)
        }
    } else {
        question?.let {
            chipGroup.children.forEach { chip ->
                chip as Chip
                chip.isEnabled = false
                if (chip.text.toString() == question.correctAnswer) {
                    chip.setChipBackgroundColorResource(R.color.teal_200)
                }
                if (selectedID == chip.id &&
                    chip.text.toString() != question.correctAnswer
                ) {
                    chip.setChipBackgroundColorResource(R.color.red_200)
                }
            }
        }
    }
}



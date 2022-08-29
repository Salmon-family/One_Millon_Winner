package com.example.onemillonwinner.util

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.core.view.children
import androidx.databinding.*
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameQuestion
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse
import com.example.onemillonwinner.util.enum.SelectAnswer
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
fun showWhenLoading(view: View, state: State<TriviaResponse>?) {
    if (state is State.Loading) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("app:isSuccess")
fun showWhenSuccess(view: View, state: State<TriviaResponse>?) {
    if (state is State.Success) {
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
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("app:buttonUpdateText")
fun updateTextButton(submitButton: Button, question: GameQuestion?) {
    question?.let {
        if (it.selectedAnswerIndex != -1) {
            submitButton.text = submitButton.context.resources.getText(R.string.next_question)
            submitButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_right, 0)
        } else {
            submitButton.text = submitButton.context.resources.getText(R.string.submit)
            submitButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
    }

}

@BindingAdapter("app:selectAnswer")
fun bindSelectAnswerChip(chipGroup: ChipGroup, question: GameQuestion?) {
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

@BindingAdapter(value = ["app:textAnswer"])
fun setAnswer(chip: Chip, answer: String?) {
    if (answer != "" && answer != null) {
        chip.text = answer.htmlText()
        chip.isEnabled = true
    } else {
        chip.isEnabled = false
    }
}


@BindingAdapter("app:formatTextFromHtml")
fun formatTextFromHtml(view: TextView, text: String?) {
    text?.let {
        view.text = it.htmlText()
    }
}
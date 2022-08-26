package com.example.onemillonwinner.util

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.data.questionResponse.TriviaResponse

@BindingAdapter("isLoadingProgress")
fun bindViewVisibilityProgressBar(view: ProgressBar, state: State<TriviaResponse>?) {
    if (state != null && state is State.Loading) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("isLoadingView")
fun bindViewVisibilityView(view: View, state: State<TriviaResponse>?) {
    if (state != null && state is State.Loading) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}
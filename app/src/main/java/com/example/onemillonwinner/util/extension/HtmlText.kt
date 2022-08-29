package com.example.onemillonwinner.util.extension

import android.widget.TextView
import androidx.core.text.HtmlCompat

fun TextView.htmlText(text: String){
    setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT))
}
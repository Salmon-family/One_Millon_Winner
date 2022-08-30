package com.example.onemillonwinner.util.extension

import androidx.core.text.HtmlCompat

fun String.htmlText(): String{
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
}
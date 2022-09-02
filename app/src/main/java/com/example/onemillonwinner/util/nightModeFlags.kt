package com.example.onemillonwinner.util

import android.content.res.Configuration
import android.view.View

fun  nightModeFlags(view: View): Int {
    return view.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
}
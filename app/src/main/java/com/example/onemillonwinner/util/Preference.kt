package com.example.onemillonwinner.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Build.VERSION_CODES.M

object Preference {
    private var sharedPrefs: SharedPreferences? = null
    private const val SHARED_PREFS_NAME = "prefs"
    private const val KEY_SCORE = "score"

    fun initPrefs(context: Context) {
        sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    var score: Int?
        get() = sharedPrefs?.getInt(KEY_SCORE, -1)
        set(value) {
            value?.let { sharedPrefs?.edit()?.putInt(KEY_SCORE, it)?.apply() }
        }
}
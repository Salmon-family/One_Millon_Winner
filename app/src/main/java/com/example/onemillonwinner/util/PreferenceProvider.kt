package com.example.onemillonwinner.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class PreferenceProvider {

    fun getInt(key: String) = sharedPrefs?.getInt(key, 0)

    fun setInt(key: String, value: Int) {
        sharedPrefs?.edit()?.putInt(key, value)?.apply()
    }

    companion object {
        private var sharedPrefs: SharedPreferences? = null
        private const val TABLE_NAME = "OneMillionWinner"

        fun initPrefs(context: Context) {
            if (sharedPrefs == null) {
                sharedPrefs = context.getSharedPreferences(TABLE_NAME, Activity.MODE_PRIVATE)
            }
        }
    }
}
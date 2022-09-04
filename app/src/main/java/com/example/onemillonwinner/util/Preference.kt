package com.example.onemillonwinner.util

import android.content.Context
import android.content.SharedPreferences
import com.example.onemillonwinner.util.Constants.TABLE_NAME

class Preference {
    private var sharedPrefs: SharedPreferences? = null

    fun initPrefs(context: Context) {
        sharedPrefs = context.getSharedPreferences(TABLE_NAME, Context.MODE_PRIVATE)
    }

    fun getInt(key: String) = sharedPrefs?.getInt(key, -1)

    fun setInt(value: Int, key: String) {
        sharedPrefs?.edit()?.putInt(key, value)?.apply()
    }
}
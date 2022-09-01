package com.example.onemillonwinner.util

import android.content.Context
import android.content.SharedPreferences

object Preference {
    private var sharedPrefs: SharedPreferences? = null
    private const val TABLE_NAME = "OneMillionWinner"

    fun initPrefs(context: Context) {
        sharedPrefs = context.getSharedPreferences(TABLE_NAME, Context.MODE_PRIVATE)
    }

    fun getInt(key: String) = sharedPrefs?.getInt(key, -1)

    fun setInt(value: Int, key: String) {
        sharedPrefs?.edit()?.putInt(key, value)?.apply()
    }
}


/*fun Context.getBestPrizeSharedPreferences(): Int {
    val sharedPreferences =
        this.getSharedPreferences(Constants.TABLE_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getInt(Constants.BEST_PRIZE, -1)
}

fun Context.saveBestPrizeSharedPreferences(prize: Int) {
    val sharedPreferences =
        this.getSharedPreferences(Constants.TABLE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt(Constants.BEST_PRIZE, prize)
    editor.apply()
}*/
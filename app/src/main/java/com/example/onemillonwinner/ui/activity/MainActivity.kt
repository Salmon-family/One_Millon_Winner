package com.example.onemillonwinner.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onemillonwinner.R
import com.example.onemillonwinner.util.Preference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_OneMillonWinner)
        setContentView(R.layout.activity_main)

        Preference().initPrefs(applicationContext)
    }
}
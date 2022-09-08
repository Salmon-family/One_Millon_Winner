package com.example.onemillonwinner.data

import com.example.onemillonwinner.util.enumState.ChoicesState

data class Choice(
    var answer: String,
    var state: ChoicesState
)
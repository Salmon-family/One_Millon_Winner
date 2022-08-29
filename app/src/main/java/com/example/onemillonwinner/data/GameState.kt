package com.example.onemillonwinner.data

enum class GameState {
    Loading,
    Success,
    Failure,
    GameComplete,

    QUESTION_START,
    ANSWER_SELECTED,
    QUESTION_SUBMITTED
}
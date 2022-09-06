package com.example.onemillonwinner.data

enum class QuestionState {
    GAME_OVER,
    QUESTION_START,
    QUESTION_SUBMITTED,
    WRONG_ANSWER_SUBMITTED
}

enum class ChoicesState {
    CORRECT,
    WRONG,
    DISABLE_SELECTION,
    SELECTED,
    NOT_SELECTED
}
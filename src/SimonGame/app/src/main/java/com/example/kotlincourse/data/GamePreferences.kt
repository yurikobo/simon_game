package com.example.kotlincourse.data

data class GamePreferences(
    val bestScore: Int,
    val isSoundOn: Boolean,
    val delayModifier: Float,
    val isButtonHighlighted: Boolean,
    val soundBank: Int
)
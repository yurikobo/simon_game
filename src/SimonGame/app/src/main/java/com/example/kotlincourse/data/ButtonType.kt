package com.example.kotlincourse.data

enum class ButtonType {
    UPPER_LEFT,
    UPPER_RIGHT,
    LOWER_LEFT,
    LOWER_RIGHT;

    companion object {
        fun random() = entries.random()
    }
}
package com.example.kotlincourse.data

import androidx.compose.ui.graphics.Color

data class GameButtonResource(
    val buttonType: ButtonType,
    val activeColor: Color,
    val inactiveColor: Color,
    val stringResource: Int,
)

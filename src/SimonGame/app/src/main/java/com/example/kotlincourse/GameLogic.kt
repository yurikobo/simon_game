package com.example.kotlincourse

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.kotlincourse.data.ButtonType

private const val DURATION = 1000L
private const val DELAY = 500L


object GameLogic {
    private val handler = Handler(requireNotNull(Looper.myLooper()))
    private val sequence = mutableListOf<ButtonType>()
    private var pointer = 0
    private val isButtonActive = mapOf(
        ButtonType.UPPER_LEFT to mutableStateOf(false),
        ButtonType.UPPER_RIGHT to mutableStateOf(false),
        ButtonType.LOWER_LEFT to mutableStateOf(false),
        ButtonType.LOWER_RIGHT to mutableStateOf(false)
    )
    private var delayModifier: Float = 1.0F
    val isPlayerTurn = mutableStateOf(true)
    val isGameOver = mutableStateOf(false)
    val currentLevel = mutableIntStateOf(0)

    private fun updateSequence() {
        ++currentLevel.intValue
        isPlayerTurn.value = false
        sequence.add(ButtonType.random())
        handler.removeCallbacksAndMessages(null)
        sequence.withIndex().forEach {
            handler.postDelayed(
                { blinkButton(it.value) },
                ((DURATION + DELAY) * delayModifier * (it.index + 1)).toLong()
            )
        }
        handler.postDelayed(
            { isPlayerTurn.value = true },
            ((DURATION + DELAY) * delayModifier * (sequence.size + 1)).toLong()
        )
    }

    private fun blinkButton(buttonType: ButtonType) {
        isButtonActive[buttonType]?.value = true
        handler.postDelayed(
            { isButtonActive[buttonType]?.value = false }, (DELAY * delayModifier).toLong()
        )
    }


    fun checkAnswer(answer: ButtonType) {
        if (sequence[pointer] == answer) {
            if (++pointer == sequence.size) {
                pointer = 0
                updateSequence()
            }
        } else {
            isGameOver.value = true
        }
    }

    fun restart() {
        isGameOver.value = false
        currentLevel.intValue = 0
        pointer = 0
        sequence.clear()
        updateSequence()
    }

    fun setDelayModifier(value: Float) {
        delayModifier = value
    }

    fun getButtonStatus(buttonType: ButtonType): MutableState<Boolean> {
        return isButtonActive[buttonType] ?: mutableStateOf(false)

    }

}
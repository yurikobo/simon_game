package com.example.kotlincourse

import android.content.Context
import android.media.MediaPlayer
import com.example.kotlincourse.data.ButtonType

object SoundPlayer {
    private var isSoundOn = true
    private var soundBankIndex = 0
    private val soundResource = mapOf(
        ButtonType.UPPER_LEFT to arrayListOf(R.raw.beep1, R.raw.drum1, R.raw.animal1),
        ButtonType.UPPER_RIGHT to arrayListOf(R.raw.beep2, R.raw.drum2, R.raw.animal2),
        ButtonType.LOWER_LEFT to arrayListOf(R.raw.beep3, R.raw.drum3, R.raw.animal3),
        ButtonType.LOWER_RIGHT to arrayListOf(R.raw.beep4, R.raw.drum4, R.raw.animal4)
    )

    fun setSoundState(state: Boolean) {
        isSoundOn = state
    }

    fun setSoundBankIndex(index: Int) {
        soundBankIndex = index
    }

    fun playButtonSoundEffect(context: Context, buttonType: ButtonType) {
        if (isSoundOn) {
            soundResource[buttonType]?.let {
                val mediaPlayer = MediaPlayer.create(context, it[soundBankIndex])
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
            }

        }
    }
}

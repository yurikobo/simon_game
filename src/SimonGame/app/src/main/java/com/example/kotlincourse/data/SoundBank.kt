package com.example.kotlincourse.data

enum class SoundBank(private val bankName: String) {
    BEEP("Beep"),
    DRUM("Drum"),
    ANIMAL("Animal");

    override fun toString(): String = bankName

}
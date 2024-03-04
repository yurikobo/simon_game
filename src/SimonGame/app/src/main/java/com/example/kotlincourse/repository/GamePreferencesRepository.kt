package com.example.kotlincourse.repository

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.kotlincourse.data.GamePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val GAME_PREFERENCES_NAME = "game_preferences"

val Context.dataStore by preferencesDataStore(
    name = GAME_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, GAME_PREFERENCES_NAME))
    }
)

data class GamePreferencesRepository(val context: Context) {
    private object PreferencesKeys {
        val BEST_SCORE = intPreferencesKey("best_score")
        val IS_SOUND_ON = booleanPreferencesKey("is_sound_on")
        val DELAY_MODIFIER = floatPreferencesKey("delay_modifier")
        val IS_BUTTON_HIGHLIGHTED = booleanPreferencesKey("is_button_highlighted")
        val SOUND_BANK = intPreferencesKey("sound_bank_index")
    }

    val gamePreferencesFlow: Flow<GamePreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val bestScore = preferences[PreferencesKeys.BEST_SCORE] ?: 0
            val soundOn = preferences[PreferencesKeys.IS_SOUND_ON] ?: true
            val delayModifier = preferences[PreferencesKeys.DELAY_MODIFIER] ?: 0.5F
            val isButtonHighlighted = preferences[PreferencesKeys.IS_BUTTON_HIGHLIGHTED] ?: true
            val soundBank = preferences[PreferencesKeys.SOUND_BANK] ?: 0
            GamePreferences(bestScore, soundOn, delayModifier, isButtonHighlighted, soundBank)
        }

    suspend fun updateSound(isSoundOn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_SOUND_ON] = isSoundOn
        }
    }

    suspend fun updateDelayModifier(delayModifier: Float) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DELAY_MODIFIER] = delayModifier
        }
    }


    suspend fun updateBestScore(bestScore: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.BEST_SCORE] = bestScore
        }
    }

    suspend fun updateButtonHighlight(isButtonHighlighted: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_BUTTON_HIGHLIGHTED] = isButtonHighlighted
        }
    }


    suspend fun updateSoundBankIndex(soundBank: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SOUND_BANK] = soundBank
        }
    }

}
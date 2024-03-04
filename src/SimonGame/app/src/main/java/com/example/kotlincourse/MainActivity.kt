package com.example.kotlincourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlincourse.repository.GamePreferencesRepository
import com.example.kotlincourse.screen.FreeGameScreen
import com.example.kotlincourse.screen.GameScreen
import com.example.kotlincourse.screen.InfoScreen
import com.example.kotlincourse.screen.MainMenuScreen
import com.example.kotlincourse.screen.SettingsScreen

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gamePreferencesRepository = GamePreferencesRepository(this)

        setContent {
            val bestScore = remember { mutableIntStateOf(0) }
            val delayModifier = remember { mutableFloatStateOf(1.0F) }
            val isSoundOn = remember { mutableStateOf(true) }
            val soundBankIndex = remember { mutableIntStateOf(0) }
            val navController = rememberNavController()
            LaunchedEffect(true) {
                gamePreferencesRepository.gamePreferencesFlow.collect { preferences ->
                    bestScore.intValue = preferences.bestScore
                    delayModifier.floatValue = preferences.delayModifier
                    isSoundOn.value = preferences.isSoundOn
                    soundBankIndex.intValue = preferences.soundBank
                }
            }
            NavHost(navController = navController, startDestination = Route.MainMenu.destination) {
                composable(Route.MainMenu.destination) {
                    MainMenuScreen(navController, delayModifier, isSoundOn, soundBankIndex)
                }
                composable(Route.NewGame.destination) {
                    GameScreen(navController, gamePreferencesRepository)
                }
                composable(Route.FreeGame.destination) {
                    FreeGameScreen(navController)
                }
                composable(Route.Settings.destination) {
                    SettingsScreen(
                        navController,
                        gamePreferencesRepository
                    )
                }
                composable(Route.About.destination) { InfoScreen(navController, bestScore) }
            }
        }
    }


}
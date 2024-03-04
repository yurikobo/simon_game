package com.example.kotlincourse.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kotlincourse.GameLogic
import com.example.kotlincourse.R
import com.example.kotlincourse.Route
import com.example.kotlincourse.SoundPlayer

@Composable
fun MainMenuScreen(
    navController: NavHostController,
    delayModifier: MutableFloatState,
    soundState: MutableState<Boolean>,
    soundBankIndex: MutableIntState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.75F)
                .padding(20.dp),
            onClick = {
                SoundPlayer.setSoundState(soundState.value)
                SoundPlayer.setSoundBankIndex(soundBankIndex.intValue)
                GameLogic.setDelayModifier(delayModifier.floatValue)
                GameLogic.restart()
                navController.navigate(Route.NewGame.destination)
            }
        ) {
            Text(text = stringResource(id = R.string.new_game_button))

        }
        NavigationButton(
            navigateTo = {
                SoundPlayer.setSoundState(soundState.value)
                SoundPlayer.setSoundBankIndex(soundBankIndex.intValue)
                navController.navigate(Route.FreeGame.destination)
            },
            textId = R.string.free_play_button
        )
        NavigationButton(
            navigateTo = { navController.navigate(Route.Settings.destination) },
            textId = R.string.settings_button
        )
        NavigationButton(
            navigateTo = { navController.navigate(Route.About.destination) },
            textId = R.string.about_button
        )
    }
}

@Composable
private fun NavigationButton(navigateTo: () -> Unit, textId: Int) {
    Button(
        modifier = Modifier
            .fillMaxWidth(0.75F)
            .padding(20.dp),
        onClick = { navigateTo() }
    ) {
        Text(text = stringResource(id = textId))

    }
}
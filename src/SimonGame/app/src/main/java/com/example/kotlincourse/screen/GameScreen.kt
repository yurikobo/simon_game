package com.example.kotlincourse.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kotlincourse.GameLogic
import com.example.kotlincourse.R
import com.example.kotlincourse.SoundPlayer
import com.example.kotlincourse.data.ButtonType
import com.example.kotlincourse.data.GameButtonResource
import com.example.kotlincourse.repository.GamePreferencesRepository
import com.example.kotlincourse.ui.theme.Blue
import com.example.kotlincourse.ui.theme.Green
import com.example.kotlincourse.ui.theme.LightBlue
import com.example.kotlincourse.ui.theme.LightGreen
import com.example.kotlincourse.ui.theme.LightRed
import com.example.kotlincourse.ui.theme.LightYellow
import com.example.kotlincourse.ui.theme.Red
import com.example.kotlincourse.ui.theme.Yellow
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
    navController: NavHostController,
    gamePreferencesRepository: GamePreferencesRepository
) {
    val bestScore = remember { mutableIntStateOf(0) }
    val isEndGame = remember { GameLogic.isGameOver }
    val isButtonHighlighted = remember { mutableStateOf(true) }
    val currentLevel = remember { GameLogic.currentLevel }
    val bestScoreUpd = remember { derivedStateOf { currentLevel.intValue > bestScore.intValue } }
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(true) {
        gamePreferencesRepository.gamePreferencesFlow.collect { preferences ->
            bestScore.intValue = preferences.bestScore
            isButtonHighlighted.value = preferences.isButtonHighlighted
        }
    }
    if (bestScoreUpd.value) {
        bestScore.intValue = currentLevel.intValue - 1
    }
    Column(modifier = Modifier.fillMaxHeight()) {
        TopBar(navController, stringResource(R.string.app_name))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F, true)
        ) {
            Text(
                color = MaterialTheme.colorScheme.background,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterVertically),
                text = stringResource(id = R.string.level_label, currentLevel.intValue)
            )
            Text(
                color = MaterialTheme.colorScheme.background,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterVertically),
                text = stringResource(R.string.record_label, bestScore.intValue),
                onTextLayout = {
                    coroutine.launch {
                        gamePreferencesRepository.updateBestScore(
                            bestScore.intValue
                        )
                    }
                }
            )
        }
        Row(
            modifier = Modifier
                .weight(3F, true)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            GameButton(
                Modifier
                    .fillMaxHeight()
                    .weight(1F, true)
                    .padding(10.dp),
                GameButtonResource(
                    buttonType = ButtonType.UPPER_LEFT,
                    activeColor = LightRed,
                    inactiveColor = Red,
                    stringResource = R.string.upper_left_button_text,
                ), isButtonHighlighted
            )
            GameButton(
                Modifier
                    .fillMaxHeight()
                    .weight(1F, true)
                    .padding(10.dp),
                GameButtonResource(
                    buttonType = ButtonType.UPPER_RIGHT,
                    activeColor = LightGreen,
                    inactiveColor = Green,
                    stringResource = R.string.upper_right_button_text,
                ), isButtonHighlighted
            )
        }
        Row(
            modifier = Modifier
                .weight(3F, true)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            GameButton(
                Modifier
                    .fillMaxHeight()
                    .weight(1F)
                    .padding(10.dp),
                GameButtonResource(
                    buttonType = ButtonType.LOWER_LEFT,
                    activeColor = LightBlue,
                    inactiveColor = Blue,
                    stringResource = R.string.lower_left_button_text,
                ), isButtonHighlighted
            )
            GameButton(
                Modifier
                    .fillMaxHeight()
                    .weight(1F)
                    .padding(10.dp),
                GameButtonResource(
                    buttonType = ButtonType.LOWER_RIGHT,
                    activeColor = LightYellow,
                    inactiveColor = Yellow,
                    stringResource = R.string.lower_right_button_text,
                ), isButtonHighlighted
            )
        }
        if (isEndGame.value) {
            AlertDialog(
                onDismissRequest = {
                    isEndGame.value = false
                    navController.navigateUp()
                },
                confirmButton = {
                    Button(onClick = { GameLogic.restart() }) {
                        Text(text = "Restart")
                    }
                },
                title = { Text(text = "GG") },
                text = {
                    Text(
                        text = stringResource(
                            R.string.game_over_message,
                            (currentLevel.intValue - 1)
                        )
                    )
                }
            )
        }
    }
}


@Composable
fun GameButton(
    modifier: Modifier,
    resource: GameButtonResource,
    isButtonHighlighted: MutableState<Boolean>
) {
    val isActive = remember { GameLogic.getButtonStatus(resource.buttonType) }
    val isPlayerTurn = remember { GameLogic.isPlayerTurn }
    val context = LocalContext.current
    if (isActive.value) SoundPlayer.playButtonSoundEffect(context, resource.buttonType)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(if (isActive.value && isButtonHighlighted.value) resource.activeColor else resource.inactiveColor)
            .clickable(enabled = isPlayerTurn.value) {
                SoundPlayer.playButtonSoundEffect(context, resource.buttonType)
                GameLogic.checkAnswer(resource.buttonType)
            }

    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black,
            fontSize = 20.sp,
            text = stringResource(id = resource.stringResource)
        )
    }
}

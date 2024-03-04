package com.example.kotlincourse.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kotlincourse.R
import com.example.kotlincourse.data.SoundBank
import com.example.kotlincourse.repository.GamePreferencesRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    gamePreferencesRepository: GamePreferencesRepository
) {
    val soundSwitchState = remember { mutableStateOf(true) }
    val soundBankIndex = remember { mutableIntStateOf(0) }
    val expanded = remember { mutableStateOf(false) }
    val soundBanks = SoundBank.entries
    val delaySliderState = remember { mutableFloatStateOf(0.5F) }
    val buttonHighlightState = remember { mutableStateOf(true) }
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(true) {
        gamePreferencesRepository.gamePreferencesFlow.collect { preferences ->
            soundSwitchState.value = preferences.isSoundOn
            delaySliderState.floatValue = preferences.delayModifier
            buttonHighlightState.value = preferences.isButtonHighlighted
            soundBankIndex.intValue = preferences.soundBank
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        TopBar(navController, stringResource(R.string.settings_toolbar_label))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                color = MaterialTheme.colorScheme.background,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterVertically),
                text = "Sound"
            )
            Switch(
                modifier = Modifier.padding(20.dp),
                checked = soundSwitchState.value,
                onCheckedChange = {
                    coroutine.launch { gamePreferencesRepository.updateSound(it) }
                    soundSwitchState.value = it
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                color = MaterialTheme.colorScheme.background,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterVertically),
                text = "Delay"
            )
            Column {
                Slider(
                    modifier = Modifier
                        .padding(20.dp)
                        .offset(y = (10).dp),
                    value = delaySliderState.floatValue,
                    onValueChange = {
                        coroutine.launch { gamePreferencesRepository.updateDelayModifier(it) }
                        delaySliderState.floatValue = it
                    },
                    valueRange = 0.1F..1.0F,
                    steps = 8
                )
                Text(
                    modifier = Modifier
                        .offset(y = (-20).dp)
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    text = "(${1000 * delaySliderState.floatValue} ms)"
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                color = MaterialTheme.colorScheme.background,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterVertically),
                text = "Button highlight"
            )
            Switch(
                modifier = Modifier.padding(20.dp),
                checked = buttonHighlightState.value,
                onCheckedChange = {
                    coroutine.launch { gamePreferencesRepository.updateButtonHighlight(it) }
                    buttonHighlightState.value = it
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                color = MaterialTheme.colorScheme.background,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterVertically),
                text = "Sound bank"
            )
            ExposedDropdownMenuBox(
                expanded = expanded.value,
                onExpandedChange = { expanded.value = !expanded.value }
            ) {
                TextField(
                    value = soundBanks[soundBankIndex.intValue].toString(),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    soundBanks.withIndex().forEach {
                        DropdownMenuItem(
                            text = { Text(it.value.toString()) },
                            onClick = {
                                expanded.value = false
                                soundBankIndex.intValue = it.index
                                coroutine.launch { gamePreferencesRepository.updateSoundBankIndex(it.index) }
                            }
                        )
                    }
                }

            }
        }

    }

}
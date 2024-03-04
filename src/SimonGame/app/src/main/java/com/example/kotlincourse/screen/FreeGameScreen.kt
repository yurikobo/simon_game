package com.example.kotlincourse.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kotlincourse.R
import com.example.kotlincourse.SoundPlayer
import com.example.kotlincourse.data.ButtonType
import com.example.kotlincourse.data.GameButtonResource
import com.example.kotlincourse.ui.theme.Blue
import com.example.kotlincourse.ui.theme.Green
import com.example.kotlincourse.ui.theme.LightBlue
import com.example.kotlincourse.ui.theme.LightGreen
import com.example.kotlincourse.ui.theme.LightRed
import com.example.kotlincourse.ui.theme.LightYellow
import com.example.kotlincourse.ui.theme.Red
import com.example.kotlincourse.ui.theme.Yellow

@Composable
fun FreeGameScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxHeight()) {
        TopBar(navController, stringResource(R.string.app_name))
        Row(
            modifier = Modifier
                .weight(3F, true)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            FreeGameButton(
                Modifier
                    .fillMaxHeight()
                    .weight(1F, true)
                    .padding(10.dp),
                GameButtonResource(
                    buttonType = ButtonType.UPPER_LEFT,
                    activeColor = LightRed,
                    inactiveColor = Red,
                    stringResource = R.string.upper_left_button_text,
                )
            )
            FreeGameButton(
                Modifier
                    .fillMaxHeight()
                    .weight(1F, true)
                    .padding(10.dp),
                GameButtonResource(
                    buttonType = ButtonType.UPPER_RIGHT,
                    activeColor = LightGreen,
                    inactiveColor = Green,
                    stringResource = R.string.upper_right_button_text,
                )
            )
        }
        Row(
            modifier = Modifier
                .weight(3F, true)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            FreeGameButton(
                Modifier
                    .fillMaxHeight()
                    .weight(1F)
                    .padding(10.dp),
                GameButtonResource(
                    buttonType = ButtonType.LOWER_LEFT,
                    activeColor = LightBlue,
                    inactiveColor = Blue,
                    stringResource = R.string.lower_left_button_text,
                )
            )
            FreeGameButton(
                Modifier
                    .fillMaxHeight()
                    .weight(1F)
                    .padding(10.dp),
                GameButtonResource(
                    buttonType = ButtonType.LOWER_RIGHT,
                    activeColor = LightYellow,
                    inactiveColor = Yellow,
                    stringResource = R.string.lower_right_button_text,
                )
            )
        }
    }
}


@Composable
fun FreeGameButton(modifier: Modifier, resource: GameButtonResource) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(resource.inactiveColor)
            .clickable { SoundPlayer.playButtonSoundEffect(context, resource.buttonType) }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black,
            fontSize = 20.sp,
            text = stringResource(id = resource.stringResource)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, stringResource: String) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = { Text(stringResource) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "NavigationUp"
                )
            }
        }
    )
}

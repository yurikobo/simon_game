package com.example.kotlincourse.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kotlincourse.R


@Composable
fun InfoScreen(navController: NavHostController, bestScore: MutableIntState) {

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        TopBar(navController, stringResource(R.string.about_toolbar_label))
        Text(
            color = MaterialTheme.colorScheme.background,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            modifier = Modifier
                .padding(20.dp),
            text = stringResource(R.string.best_score_info, bestScore.intValue)
        )
        Text(
            color = MaterialTheme.colorScheme.background,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            modifier = Modifier
                .padding(20.dp),
            text = stringResource(R.string.game_conditions)
        )
        Text(
            color = MaterialTheme.colorScheme.background,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            modifier = Modifier
                .padding(20.dp),
            text = stringResource(R.string.author_info)
        )
    }
}

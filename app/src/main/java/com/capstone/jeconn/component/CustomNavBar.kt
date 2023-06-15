package com.capstone.jeconn.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CustomNavbar(
    modifier: Modifier = Modifier,
    padding: Dp = 12.dp,
    content: @Composable RowScope.() -> Unit,
) {
    val theme = MaterialTheme.colorScheme

    NavigationBar(
        containerColor = theme.secondary,
        contentColor = Color.White,
        modifier = modifier
    ) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = theme.secondary
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = padding),
            content = content
        )
    }
}
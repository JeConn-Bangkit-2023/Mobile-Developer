package com.capstone.jeconn.ui.screen.dashboard.status_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.pager.Tabs
import com.capstone.jeconn.component.pager.TabsContent
import com.capstone.jeconn.ui.screen.dashboard.status_screen.tabs.CancelledTab
import com.capstone.jeconn.ui.screen.dashboard.status_screen.tabs.DoneTab
import com.capstone.jeconn.ui.screen.dashboard.status_screen.tabs.ProcessTab
import com.capstone.jeconn.ui.screen.dashboard.status_screen.tabs.UnpaidTab
import com.capstone.jeconn.ui.screen.dashboard.status_screen.tabs.WaitingTab
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StatusScreen(navHostController: NavHostController, myPaddingValues: PaddingValues) {
    val context = LocalContext.current

    val tabs = listOf(
        TabsItem(context.getString(R.string.unpaid)) { UnpaidTab(navHostController = navHostController) },
        TabsItem(context.getString(R.string.process)) { ProcessTab(navHostController = navHostController) },
        TabsItem(context.getString(R.string.waiting)) { WaitingTab(navHostController = navHostController) },
        TabsItem(context.getString(R.string.done)) { DoneTab(navHostController = navHostController) },
        TabsItem(context.getString(R.string.canceled)) { CancelledTab(navHostController = navHostController) },
    )

    val pagerState = rememberPagerState()

    Column() {
        Tabs(tabs = tabs, pagerState = pagerState)
        TabsContent(tabs = tabs, pagerState = pagerState)
    }

}

data class TabsItem(
    val tabName: String,
    val content: @Composable () -> Unit
)
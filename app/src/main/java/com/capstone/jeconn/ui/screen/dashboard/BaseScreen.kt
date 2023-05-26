package com.capstone.jeconn.ui.screen.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.capstone.jeconn.R
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.ui.screen.dashboard.freelancer_screen.FreelancerScreen
import com.capstone.jeconn.ui.screen.dashboard.home_screen.HomeScreen
import com.capstone.jeconn.ui.screen.dashboard.profile_screen.ProfileScreen
import com.capstone.jeconn.ui.screen.dashboard.status_screen.StatusScreen
import com.capstone.jeconn.ui.screen.dashboard.vacancies_screen.VacanciesScreen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseScreen(
    navHostController: NavHostController,
) {
    val context = LocalContext.current
    var contentRoute by remember {
        mutableStateOf(0)
    }

    BackHandler {
        if (contentRoute == 0) {
            (context as Activity).finish()
        } else {
            contentRoute = 0
        }
    }

    val screens = listOf(
        DashboardContent(
            title = context.getString(R.string.home),
            icon = R.drawable.ic_home,
            content = { HomeScreen(navHostController = navHostController) }
        ),
        DashboardContent(
            title = context.getString(R.string.vacancies),
            icon = R.drawable.ic_vacancies,
            content = { VacanciesScreen(navHostController = navHostController) }
        ),
        DashboardContent(
            title = context.getString(R.string.freelance),
            icon = R.drawable.ic_freelancer,
            content = { FreelancerScreen(navHostController = navHostController) }
        ),
        DashboardContent(
            title = context.getString(R.string.status),
            icon = R.drawable.ic_status,
            content = { StatusScreen(navHostController = navHostController) }
        ),
        DashboardContent(
            title = context.getString(R.string.profile),
            icon = R.drawable.ic_profile,
            content = { ProfileScreen(navHostController = navHostController) }
        ),
    )
    val currentTheme = isSystemInDarkTheme()

    val shadowColor = if (currentTheme) {
        Color.Black.copy(alpha = 0.2f) // Dark Theme
    } else {
        Color.Black.copy(alpha = 0.02f) // Light Theme
    }
    val shadowElevation = 1.dp

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.drawWithContent {
                    drawContent()
                    drawRect(
                        color = shadowColor,
                        topLeft = Offset(0f, size.height),
                        size = Size(size.width, shadowElevation.toPx()),
                        style = Fill
                    )
                    drawRect(
                        color = shadowColor,
                        topLeft = Offset(-shadowElevation.toPx(), -shadowElevation.toPx()),
                        size = Size(
                            size.width + shadowElevation.toPx() * 2,
                            shadowElevation.toPx() * 2
                        ),
                        style = Fill
                    )
                }
            ) {
                screens.forEachIndexed { index, item ->
                    val selected = contentRoute == index

                    val iconSelectedColor =
                        if (selected) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.onBackground
                    val fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal

                    NavigationBarItem(
                        selected = selected,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.secondary
                        ),
                        onClick = {
                            contentRoute = index
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 10.sp,
                                fontFamily = Font.QuickSand,
                                fontWeight = fontWeight,
                            )
                        },
                        icon = {
                            Icon(
                                painterResource(item.icon),
                                tint = iconSelectedColor,
                                contentDescription = null,
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        screens[contentRoute].content.invoke(paddingValues)
    }
}

data class DashboardContent(
    val title: String,
    val icon: Int,
    val content: @Composable (PaddingValues) -> Unit = {}
)
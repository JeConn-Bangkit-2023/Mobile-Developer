package com.capstone.jeconn.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.capstone.jeconn.R
import com.capstone.jeconn.component.Font
import com.capstone.jeconn.navigation.NavRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    navHostController: NavHostController,
    content: @Composable (PaddingValues) -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            BottomBar(navHostController)
        }
    ) { paddingValues ->
        content.invoke(paddingValues)
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val context = LocalContext.current

    val screens = listOf(
        DashboardContent(
            screen = NavRoute.HomeScreen,
            title = context.getString(R.string.home),
            icon = R.drawable.ic_home
        ),
        DashboardContent(
            screen = NavRoute.VacanciesScreen,
            title = context.getString(R.string.vacancies),
            icon = R.drawable.ic_vacancies
        ),
        DashboardContent(
            screen = NavRoute.FreelancerScreen,
            title = context.getString(R.string.freelance),
            icon = R.drawable.ic_freelancer
        ),
        DashboardContent(
            screen = NavRoute.StatusScreen,
            title = context.getString(R.string.status),
            icon = R.drawable.ic_status
        ),
        DashboardContent(
            screen = NavRoute.ProfileScreen,
            title = context.getString(R.string.profile),
            icon = R.drawable.ic_profile
        ),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomDestination = screens.any { it.screen.route == currentDestination?.route }
    if (bottomDestination) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.onPrimary
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

data class DashboardContent(
    val screen: NavRoute,
    val title: String,
    val icon: Int
)

@Composable
fun RowScope.AddItem(
    screen: DashboardContent,
    currentDestination: NavDestination?,
    navController: NavController
) {

    val isSelected = rememberSaveable {
        mutableStateOf(
            currentDestination?.hierarchy?.any {
                it.route == screen.screen.route
            } == true
        )
    }
    val backgroundColor =
        if (isSelected.value) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary
    val clip = if (isSelected.value) RoundedCornerShape(25.dp) else RoundedCornerShape(0.dp)
    val selectedColor =
        if (isSelected.value) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    BottomNavigationItem(
        label = {
            Text(
                text = screen.title,
                color = selectedColor,
                fontSize = 10.sp,
                fontFamily = Font.QuickSand,
                fontWeight = FontWeight.Medium
            )
        },
        icon = {
            Icon(
                painterResource(screen.icon),
                contentDescription = null
            )
        },
        selected = isSelected.value,
        modifier = Modifier
            .clip(clip)
            .background(backgroundColor),
        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
        unselectedContentColor = Color(0x30000000),
        onClick = {
            navController.navigate(screen.screen.route) {
                popUpTo(NavRoute.HomeScreen.route)
                launchSingleTop = true
            }
        }
    )
}
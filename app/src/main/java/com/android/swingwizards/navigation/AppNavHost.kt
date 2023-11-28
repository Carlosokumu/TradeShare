package com.android.swingwizards.navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.swingwizards.components.AccountScreen
import com.android.swingwizards.components.HomeScreen
import com.android.swingwizards.components.InsightsScreen
import com.android.swingwizards.components.TradesScreen
import com.android.swingwizards.models.Screen
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.utils.AppUtils.navigationScreens


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.HOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        composable(route = Screen.Trades.route) {
            TradesScreen()
        }
        composable(route = Screen.Insights.route) {
            InsightsScreen()
        }
        composable(route = Screen.Account.route) {
            AccountScreen()
        }
    }
}


@Composable
fun BottomNavBar(navController: NavController, visible: Boolean = true) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        NavigationBar(
            containerColor = AppTheme.colors.background,
            modifier = Modifier
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            navigationScreens.forEach { screen ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route)

                    },
                    icon = {
                        Icon(painter = painterResource(id = screen.icon), contentDescription = null)
                    },
                    label = {
                        Text(
                            text = stringResource(id = screen.label),
                            style = AppTheme.typography.caption
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AppTheme.colors.textPrimary,
                        indicatorColor = AppTheme.colors.onPrimary,
                        selectedTextColor = AppTheme.colors.textPrimary
                    )
                )
            }
        }
    }
}
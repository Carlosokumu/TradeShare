package com.android.swingwizards.navigation

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.android.swingwizards.activities.WebViewActivity
import com.android.swingwizards.screens.HomeFeed
import com.android.swingwizards.components.InsightsScreen
import com.android.swingwizards.screens.MetaTraderConnectionScreen
import com.android.swingwizards.screens.ProfileScreen
import com.android.swingwizards.screens.TradeHistory
import com.android.swingwizards.screens.TradersScreen
import com.android.swingwizards.screens.TradingAccounts
import com.android.swingwizards.screens.TradingPlatformComponent
import com.android.swingwizards.models.Screen
import com.android.swingwizards.screens.Portfolio
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.utils.AppUtils
import com.android.swingwizards.utils.AppUtils.navigationScreens
import com.android.swingwizards.viewmodels.SignUpProcessViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun DashboardNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    context: Context,
    startDestination: String = Screen.HOME_FEED
) {
    val signUpProcessViewModel: SignUpProcessViewModel = getViewModel()
    val tradingPlatforms by signUpProcessViewModel.tradingPlatforms.collectAsState()
    val selectedPlatform by signUpProcessViewModel.selectedPlatform.collectAsState()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.HomeFeed.route) {
            HomeFeed(navController)
        }
        composable(route = Screen.Traders.route) {
            TradersScreen(navController)
        }
        composable(route = Screen.Portfolio.route) {
            Portfolio()
        }
        composable(route = Screen.Account.route) {
            ProfileScreen(navController)
        }
        composable(route = "connect") {
            TradingPlatformComponent(
                modifier = Modifier.fillMaxSize(),
                tradingPlatForms = tradingPlatforms,
                onTradingPlatformClick = { tradingPlatformEntity ->
                    signUpProcessViewModel.selectTradingPlatform(tradingPlatformEntity)
                },
                selectedTradingPlatformEntity = selectedPlatform,
                onButtonClick = {
                    when (selectedPlatform?.id) {
                        1 -> {
                            AppUtils.launchActivity(context = context, WebViewActivity::class.java)
                        }

                        2 -> {
                            navController.navigate("mt")
                        }
                    }
                }

            )
        }
        composable(route = "mt") {
            MetaTraderConnectionScreen(navController)
        }
        composable(
            route = "trade-history/{username}/{accountId}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("accountId") { type = NavType.StringType })
        ) { backStackEntry ->
            TradeHistory(
                backStackEntry.arguments?.getString("username"),
                backStackEntry.arguments?.getString("accountId")
            )
        }
        composable(route = "trading_accounts") {
            TradingAccounts(navController)
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
                        indicatorColor = AppTheme.colors.secondaryVariant,
                        selectedTextColor = AppTheme.colors.textPrimary
                    )
                )
            }
        }
    }
}
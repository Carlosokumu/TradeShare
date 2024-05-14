package com.android.swingwizards.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.swingwizards.R
import com.android.swingwizards.common.AppLoadingWheel
import com.android.swingwizards.components.AccountPerformanceGraphPager
import com.android.swingwizards.models.UiState
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.viewmodels.TradersViewModel
import com.carlos.model.DomainTrader
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun TradersScreen(navController: NavController) {
    val tradersViewModel: TradersViewModel = getViewModel()
    val uiState: UiState by tradersViewModel.uiState.collectAsState(initial = UiState.Relaxed)
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.background(AppTheme.colors.background),
        containerColor = AppTheme.colors.background
    ) {
        LazyColumn(
            modifier = Modifier
                .consumeWindowInsets(it)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            AppTheme.colors.background.copy(alpha = 0.05f),
                            AppTheme.colors.background.copy(alpha = 0.02f),
                        )
                    )
                ), contentPadding = it, state = lazyListState,
            verticalArrangement = Arrangement.Center
        ) {

            item {
                Row(
                    verticalAlignment = CenterVertically, modifier = Modifier.padding(all = 10.dp)
                ) {
                    Text(
                        text = "Top Performing",
                        color = AppTheme.colors.textPrimary,
                        style = AppTheme.typography.subtitle,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(all = 10.dp),
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.about),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = AppTheme.colors.textPrimary
                    )
                }

            }

            item {
                when (uiState) {
                    is UiState.Error -> {

                    }

                    UiState.Loading -> {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            AppLoadingWheel(contentDesc = "Content")
                        }

                    }

                    is UiState.Success<*> -> {
                        val response = (uiState as UiState.Success<List<DomainTrader>>).response
                        AccountPerformanceGraphPager(
                            traders = response, navController = navController
                        )
                    }

                    is UiState.ServerError -> {

                    }

                    else -> {}
                }
            }
            item {
                Row(
                    verticalAlignment = CenterVertically, modifier = Modifier.padding(all = 10.dp)
                ) {
                    Text(
                        text = "Under 1000$",
                        color = AppTheme.colors.textPrimary,
                        style = AppTheme.typography.subtitle,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(all = 10.dp),
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.about),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = AppTheme.colors.textPrimary
                    )
                }

            }

            item {
                when (uiState) {
                    is UiState.Error -> {

                    }

                    UiState.Loading -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            AppLoadingWheel(contentDesc = "Content")
                        }
                    }

                    is UiState.Success<*> -> {
                        val response = (uiState as UiState.Success<List<DomainTrader>>).response
                        AccountPerformanceGraphPager(
                            traders = response, navController = navController
                        )
                    }

                    is UiState.ServerError -> {

                    }

                    else -> {}
                }
            }
        }

    }


}


@Composable
fun TradersListScreen() {

}


@Preview
@Composable
fun HomeScreenPreview() {
    TradersScreen(rememberNavController())
}
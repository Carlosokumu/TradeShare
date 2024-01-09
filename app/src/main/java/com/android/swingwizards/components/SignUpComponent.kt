package com.android.swingwizards.components

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.android.swingwizards.activities.Dashboard
import com.android.swingwizards.activities.WebViewActivity
import com.android.swingwizards.data.local.entity.TradingPlatformEntity
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.utils.AppUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpComponent(
    tradingPlatformEntity: TradingPlatformEntity?,
    tradingPlatForms: List<TradingPlatformEntity>,
    onTradingPlatformClick: (TradingPlatformEntity) -> Unit,
    navController: NavController,
    context: Context
) {
    val components = listOf(
        Component {
            WhoAreYouComponent(
                onInvestorClicked = {},
                onTraderClicked = {

                },
                modifier = Modifier.fillMaxSize()
            )

        }, Component {
            TradingPlatformComponent(
                modifier = Modifier.fillMaxSize(),
                selectedTradingPlatformEntity = tradingPlatformEntity,
                tradingPlatForms = tradingPlatForms,
                onTradingPlatformClick = {
                    onTradingPlatformClick(it)
                },
                onButtonClick = {}
            )

        }
    )

    val pagerState = rememberPagerState {
        components.size
    }
    val coroutineScope = rememberCoroutineScope()
    val flingBehavior = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(1),
        lowVelocityAnimationSpec = tween(
            easing = FastOutLinearInEasing,
            durationMillis = 5_000
        ),
        highVelocityAnimationSpec = rememberSplineBasedDecay(),
        snapAnimationSpec = tween(
            easing = FastOutSlowInEasing,
            durationMillis = 1_000
        )
    )

    Scaffold(

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.background),
            ) {
                HorizontalPager(
                    state = pagerState, userScrollEnabled = false,
                    flingBehavior = flingBehavior
                ) { page ->
                    when (page) {
                        0 -> {
                            WhoAreYouComponent(
                                onInvestorClicked = {
                                    AppUtils.launchActivity(context, Dashboard::class.java)
                                },
                                onTraderClicked = {
                                    coroutineScope.launch {
                                        withContext(Dispatchers.Main) {
                                            pagerState.scrollToPage(
                                                pagerState.currentPage + 1
                                            )
                                        }

                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        1 -> {
                            TradingPlatformComponent(
                                modifier = Modifier,
                                selectedTradingPlatformEntity = tradingPlatformEntity,
                                tradingPlatForms = tradingPlatForms,
                                onTradingPlatformClick = {
                                    onTradingPlatformClick(it)
                                },
                                onButtonClick = {
                                    if (tradingPlatformEntity != null) {
                                        when (tradingPlatformEntity.id) {
                                            1 -> {
                                                AppUtils.launchActivity(
                                                    context,
                                                    WebViewActivity::class.java
                                                )
                                            }

                                            else -> {
                                                Log.d("ID:", tradingPlatformEntity.id.toString())
                                                navController.navigate("mt")
                                            }
                                        }
                                    }
                                })
                        }
                    }
                }
            }
        }
    }

}
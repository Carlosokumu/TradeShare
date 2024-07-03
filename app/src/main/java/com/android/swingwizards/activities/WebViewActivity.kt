package com.android.swingwizards.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.android.swingwizards.R
import com.android.swingwizards.common.AppBar
import com.android.swingwizards.components.CTraderWebComponent
import com.android.swingwizards.components.Component
import com.android.swingwizards.components.LoadingScreen
import com.android.swingwizards.screens.StepsComponent
import com.android.swingwizards.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.trade_share_black)
        setContent {
            WebViewComponent()
        }
    }
}


@Composable
fun AppBarWithProgress(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(AppTheme.colors.background)
            .fillMaxWidth()
    ) {
        AppBar(onBackClicked = onBackClicked)
        androidx.compose.material.Text(
            text = stringResource(id = R.string.ctrader_console),
            style = AppTheme.typography.subtitle,
            color = AppTheme.colors.textPrimary
        )

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WebViewComponent() {
    var isLoading by remember { mutableStateOf(false) }
    val components = listOf(
        Component {
            StepsComponent(onAppClick = {})

        }, Component {
            CTraderWebComponent(onComplete = {})
        },
        Component {
            LoadingScreen(loadingMessage = "")
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
    HorizontalPager(
        state = pagerState, userScrollEnabled = false,
        flingBehavior = flingBehavior
    ) { page ->
        when (page) {
            0 -> StepsComponent(onAppClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.Main) {
                        delay(100)
                        pagerState.scrollToPage(
                            pagerState.currentPage + 1
                        )
                    }

                }
            })

            1 -> CTraderWebComponent(onComplete = {
                isLoading = true
            })

            2 -> {
                LoadingScreen(loadingMessage = "please wait....")
            }
        }

        LaunchedEffect(key1 = isLoading) {
            if (isLoading) {
                // Add your loading indicator composable here
                coroutineScope.launch {
                    withContext(Dispatchers.Main) {
                        delay(100)
                        pagerState.scrollToPage(
                            pagerState.currentPage + 1
                        )
                    }

                }
            }
        }
    }

}

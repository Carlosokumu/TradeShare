package com.android.swingwizards.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import com.google.accompanist.pager.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.remember
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.swingwizards.drawColoredShadow
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.viewmodels.TradersViewModel
import com.carlos.model.DomainTrader
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountPerformanceGraphPager(
    modifier: Modifier = Modifier,
    traders: List<DomainTrader>,
    navController: NavController,
    pagerState: PagerState = rememberPagerState { traders.size }

) {
    LaunchedEffect(pagerState) {
        snapshotFlow { "${pagerState.currentPage} ${pagerState.pageCount}" }.collect { page ->
            Log.d("HomeSensorGraphPager", "pager 2: $page")
            // viewModel.setActivePage(pagerState.currentPage)
        }
    }
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp),
        key = {
            if (it < traders.size) {

                traders[it].username
            } else {
                -1
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),

        ) { page ->
        Card(Modifier
            .graphicsLayer {
                val pageOffset =
                    ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                lerp(
                    start = 0.85f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    Log.d("HomeSensorGraphPager", " scale: $scale")
                    scaleX = scale
                    scaleY = scale
                }
                // We animate the alpha, between 50% and 100%
                alpha = lerp(
                    start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }
            .fillMaxWidth()
            .height(125.dp)
            .clickable(onClick = {
            })
            .background(
                brush = Brush.radialGradient(

                    listOf(
                        Color(
                            ColorUtils.blendARGB(
                                Color(0xFFebdfe1).toArgb(), Color(0xFF0C100E).toArgb(), 0.8f
                            )
                        ),
                        Color(
                            ColorUtils.blendARGB(
                                Color(0xFFebdfe1).toArgb(), Color(0xFF0C100E).toArgb(), 0.97f
                            )
                        ),
                    ), center = Offset(200f, -30f),

                    radius = 600.0f
                ), shape = RoundedCornerShape(28.dp)
            )
            .drawColoredShadow(
                AppTheme.colors.background,
                offsetY = 12.dp,
                shadowRadius = 12.dp,
                borderRadius = 32.dp,
                alpha = 0.1f
            ), shape = RoundedCornerShape(28.dp), border = BorderStroke(
            brush = Brush.verticalGradient(
                listOf(
                    Color(0xFFebdfe1).copy(alpha = 0.1f),
                    Color(0xFFebdfe1).copy(alpha = 0.25f),
                )
            ),
            width = 1.dp,
        ), colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),

                ) {

                TraderItem(
                    trader = traders[page], index = 1.toString(), navController = navController
                )
            }

        }
    }

}
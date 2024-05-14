package com.android.swingwizards.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.android.swingwizards.R
import com.android.swingwizards.common.PortfolioTopAppBar
import com.android.swingwizards.theme.AppTheme
import ir.mahozad.android.PieChart


@Composable
fun Portfolio() {

    Scaffold(topBar = {
        PortfolioTopAppBar()
    }, containerColor = AppTheme.colors.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PieChartView()
        }
    }
}


@Composable
fun PieChartView() {
    AndroidView(
        modifier = Modifier.fillMaxWidth(0.7f),
        factory = { context ->
            PieChart(context).apply {
                slices = listOf(
                    PieChart.Slice(0.2f, resources.getColor(R.color.pressed_bg), label = "12",legend = "Legend A"),
                    PieChart.Slice(0.4f, resources.getColor(R.color.blue_black_light),legend = "Legend A"),
                    PieChart.Slice(0.3f, resources.getColor(R.color.navy_blue),legend = "Legend A"),
                    PieChart.Slice(0.1f, resources.getColor(R.color.blue_grey_light),legend = "Legend A")
                )
                legendsColor = resources.getColor(R.color.filter_text_color)
            }
        },
        update = { view ->
            // View's been inflated or state read in this block has been updated
            // Add logic here if necessary
        }
    )
}
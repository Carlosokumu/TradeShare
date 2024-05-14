package com.android.swingwizards.screens


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.swingwizards.common.AppLoadingWheel
import com.android.swingwizards.common.LineChart
import com.android.swingwizards.common.OpenTradeItem
import com.android.swingwizards.common.SegmentText
import com.android.swingwizards.common.SegmentedControl
import com.android.swingwizards.common.TradeHistoryItem
import com.android.swingwizards.common.TradeHistoryPeriod
import com.android.swingwizards.common.TradesTopBar
import com.android.swingwizards.common.isNumberNegative
import com.android.swingwizards.components.AccountDetailsCard
import com.android.swingwizards.enums.TradeHistoryRange
import com.android.swingwizards.mapCoinWithMarketDataUiItemsList
import com.android.swingwizards.models.DataPoint
import com.android.swingwizards.roundOffDecimal
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.viewmodels.TradeHistoryViewModel
import com.carlos.model.DomainAccountMetrics
import com.carlos.model.Trade
import com.carlos.network.models.GraphData
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TradeHistory(traderName: String?, accountId: String?, isSelf: Boolean = false) {

    val tradeHistoryViewModel: TradeHistoryViewModel = getViewModel()
    var filters by remember { mutableStateOf(tradeHistoryFilters()) }

    var defaultSelectedFilter by remember { mutableStateOf(filters.firstOrNull()) }
    var selectedTab by remember { mutableStateOf("OverView") }
    var username by remember { mutableStateOf("username") }
    val lifecycleOwner = LocalLifecycleOwner.current
    var expanded by remember { mutableStateOf(false) }
    var shouldExpand by remember { mutableStateOf(false) }

    val historicalTrades = tradeHistoryViewModel.tradeHistoryUiState.historicalTrades
    val groupedTrades = historicalTrades.groupBy { it.createdAt }
    tradeHistoryViewModel.username.observe(lifecycleOwner) { userName ->
        username = userName
    }


    if (accountId != null) {
        tradeHistoryViewModel.getMetrics(
            accountId,
            tradeHistoryViewModel.tradeHistoryUiState.timeRangeSelected.timeRange
        )
        tradeHistoryViewModel.getAccountTrades(accountId = accountId)
        tradeHistoryViewModel.fetchHistoricalTrades(
            offset = 0,
            accountId = accountId,
            range = 1
        )
    }


    Scaffold(
        topBar = {
            TradesTopBar(
                username = traderName ?: "Trader name",
                onBackClicked = {},
                onSwitchPeriodClick = {
                    shouldExpand = true
                    expanded = !expanded
                })
        },
        floatingActionButton = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(all = 15.dp), horizontalAlignment = CenterHorizontally
//            ) {
//                CommentSection()
//            }

        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = AppTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
    ) { paddingValues ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background)
                .padding(paddingValues)
        ) {


            TradeHistoryPeriod(shouldExpand = shouldExpand, selectedPeriod = {
                expanded = true
                if (accountId != null) {
                    tradeHistoryViewModel.onPeriodSelected(it, accountId)
                }

            }, shouldClose = {
                shouldExpand = false
            })



            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.background),
                contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = CenterHorizontally,
            ) {


                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.padding(5.dp)
                    ) {
                        items(filters, key = { it.title }) { filter ->
                            FilterItem(
                                filter = filter,
                                onClick = {
                                    filters = filters.map { it.copy(isSelected = it == filter) }
                                    defaultSelectedFilter =
                                        if (filter == defaultSelectedFilter) null else filter
                                    selectedTab = filter.title
                                }
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }
                when (selectedTab) {
                    "OverView" -> {
                        item {
                            OverViewSection(
                                tradeHistoryViewModel = tradeHistoryViewModel,
                                accountId = accountId
                            )
                        }
                    }

                    "Trades" -> {
                        item {
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        groupedTrades.forEach { (createdAt, trades) ->
                            stickyHeader {
                                TradesHeader(
                                    text = createdAt,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 16.dp)
                                )
                            }
                            itemsIndexed(
                                trades,
                            ) { index: Int, trade: Trade ->
                                TradeHistoryItem(
                                    trade = trade, onTradeClicked = { },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                if (index < trades.lastIndex)
                                    Divider(
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            end = 16.dp
                                        ),
                                        color = AppTheme.colors.onPrimary
                                    )
                            }
                        }
                    }


                    "Advanced Stats" -> {
                        //Implement the UI Screen for advanced stats
                        item {
                            Column(modifier = Modifier.padding(all = 10.dp)) {
                                AdvancedStatsUi()
                            }

                        }

                    }
                }


            }

        }


    }
}


@Composable
fun FilterItem(
    modifier: Modifier = Modifier,
    filter: Filter,
    onClick: (Filter) -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, color = AppTheme.colors.onSurface),
        onClick = { onClick(filter) },
        color = if (filter.isSelected) AppTheme.colors.onPrimary
        else AppTheme.colors.background
    ) {
        Text(
            modifier = Modifier.padding(
                vertical = 10.dp,
                horizontal = 15.dp
            ),
            text = filter.title, style = AppTheme.typography.button,
            color = if (filter.isSelected) {
                AppTheme.colors.textPrimary
            } else AppTheme.colors.textPrimary
        )
    }
}


@Composable
fun TradesHeader(modifier: Modifier = Modifier, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = text, style = AppTheme.typography.subtitle,
            color = AppTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}


@Composable
fun LoadingSection() {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        AppLoadingWheel(contentDesc = "Trades Loading")
    }
}

@Preview
@Composable
fun FilterItemPreview() {
    FilterItem(filter = Filter("Overview", isSelected = true), onClick = {})
}


data class Filter(
    val title: String,
    val isSelected: Boolean
)


@Preview
@Composable
fun ShowSegmentedSection() {
    SegmentedControl(
        TradeHistoryRange.values().toList().toImmutableList(),
        TradeHistoryRange.values().first(),
        onSegmentSelected = {
            //viewModel.onTimeRangeSelected(it)
        },
        modifier = Modifier
            .heightIn(min = 56.dp)
            .padding(horizontal = 8.dp)
    ) {
        SegmentText(it.uiString)
    }
}


@Composable
fun HeaderRow(header: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = header, style = AppTheme.typography.button,
            color = AppTheme.colors.textPrimary
        )
        IconButton(
            onClick = { }, modifier = Modifier
                .height(30.dp)
                .width(30.dp)
                .padding(5.dp)
        ) {
            Icon(
                Icons.Outlined.Info,
                contentDescription = "Localized description",
                tint = AppTheme.colors.onSurface
            )
        }
    }
}


@Preview
@Composable
fun OverViewPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        HeaderRow(header = "Header")
        Spacer(modifier = (Modifier.size(15.dp)))
        Row(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),

                data = mapCoinWithMarketDataUiItemsList(dataPoints),
                graphColor = Color.Red,
                showDashedLine = true,
                showYLabels = false
            )
        }
        SegmentedControl(
            TradeHistoryRange.values().toList().toImmutableList(),
            TradeHistoryRange.values().first(),
            onSegmentSelected = {
            },
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(horizontal = 8.dp)
        ) {
            SegmentText(it.uiString)
        }
    }
}


@Composable
fun AccountStats(
    metric: DomainAccountMetrics<GraphData>,
    tradeHistoryViewModel: TradeHistoryViewModel
) {

    val dataPoints = tradeHistoryViewModel.tradeHistoryUiState.dataPoints

    var periodOutCome by remember { mutableDoubleStateOf(0.0) }




    if (dataPoints.isNotEmpty()) {
        val (lowerValue, upperValue) = remember(key1 = dataPoints) {
            Pair(
                dataPoints.minBy { it.y },
                dataPoints.maxBy { it.y }
            )
        }
        periodOutCome = upperValue.y - lowerValue.y
    }



    Column(verticalArrangement = Arrangement.SpaceAround) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
        ) {
            Column(horizontalAlignment = CenterHorizontally) {
                Text(
                    text = "Today", style = AppTheme.typography.body,
                    color = AppTheme.colors.textPrimary
                )
                Text(
                    text = (roundOffDecimal(metric.periods["today"]?.profit)
                        ?: "--").toString(),
                    style = AppTheme.typography.caption,
                    color = if (metric.periods["today"]?.profit == null) AppTheme.colors.textPrimary else if (metric.periods["today"]!!.profit!! < 1.0) Color.Red else Color.Green
                )

            }
            Column(horizontalAlignment = CenterHorizontally) {
                Text(
                    text = "Week", style = AppTheme.typography.body,
                    color = AppTheme.colors.textPrimary
                )
                Text(
                    text = (roundOffDecimal(metric.periods["thisWeek"]?.profit)
                        ?: "--").toString(), style = AppTheme.typography.caption,
                    color = if (metric.periods["thisWeek"]?.profit == null) AppTheme.colors.textPrimary else if (metric.periods["thisWeek"]!!.profit!! < 1.0) Color.Red else Color.Green
                )
            }
            Column(horizontalAlignment = CenterHorizontally) {
                Text(
                    text = "Month", style = AppTheme.typography.body,
                    color = AppTheme.colors.textPrimary
                )
                Text(
                    text = (roundOffDecimal(metric.periods["thisMonth"]?.profit)
                        ?: "--").toString(), style = AppTheme.typography.caption,
                    color = if (metric.periods["thisMonth"]?.profit == null) AppTheme.colors.textPrimary else if (metric.periods["thisMonth"]!!.profit!! < 1.0) Color.Red else Color.Green
                )
            }
            Column(horizontalAlignment = CenterHorizontally) {
                Text(
                    text = "Year", style = AppTheme.typography.body,
                    color = AppTheme.colors.textPrimary
                )
                Text(
                    text = (roundOffDecimal(metric.periods["thisYear"]?.profit)
                        ?: "--").toString(), style = AppTheme.typography.caption,
                    color = if (metric.periods["thisYear"]?.profit == null) AppTheme.colors.textPrimary else if (metric.periods["thisYear"]!!.profit!! < 1.0) Color.Red else Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Performance", style = AppTheme.typography.body,
                color = AppTheme.colors.textPrimary
            )
            Card(
                modifier = Modifier
                    .sizeIn(minWidth = 72.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isNumberNegative(periodOutCome)) Color.Red else Color.Green,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = periodOutCome.toString(),
                    color = AppTheme.colors.textPrimary,
                    style = AppTheme.typography.body,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 1.dp)
                        .align(Alignment.End),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }
        }

    }
}


@Composable
fun CommentSection(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val borderColor = AppTheme.colors.secondaryVariant

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        trailingIcon = { Icon(imageVector = Icons.Default.Send, contentDescription = null) },
        onValueChange = {
            text = it
        },
        placeholder = { Text(text = "Add Comment") },
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = borderColor,
            focusedTextColor = AppTheme.colors.textPrimary,
            focusedBorderColor = AppTheme.colors.onSurface,
            unfocusedBorderColor = AppTheme.colors.onSurface,
        )
    )
}


@Composable
fun OverViewSection(tradeHistoryViewModel: TradeHistoryViewModel, accountId: String?) {
    val openTrades = tradeHistoryViewModel.tradeHistoryUiState.openTrades
    val dataPoints = tradeHistoryViewModel.tradeHistoryUiState.dataPoints

    val metric = tradeHistoryViewModel.metrics.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        HeaderRow(header = "Account Stats")
        Spacer(modifier = (Modifier.size(15.dp)))
        AccountStats(metric = metric.value, tradeHistoryViewModel = tradeHistoryViewModel)
        Spacer(modifier = Modifier.size(15.dp))
        HeaderRow(header = "Account Equity Chart")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
                .padding(start = 30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically
        ) {

            if (dataPoints.isEmpty()) {
                Text(
                    text = "No Chart for this period !",
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary
                )
            } else {
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f),
                    data = mapCoinWithMarketDataUiItemsList(
                        dataPoints
                    ),
                    graphColor = Color.Red,
                    showDashedLine = true,
                    showYLabels = true
                )
            }

        }
        Spacer(modifier = Modifier.size(10.dp))


        SegmentedControl(
            TradeHistoryRange.values().toList().toImmutableList(),
            tradeHistoryViewModel.tradeHistoryUiState.timeRangeSelected,
            onSegmentSelected = { timeRage ->
                if (accountId != null) {
                    tradeHistoryViewModel.onTimeRangeSelected(timeRage, accountId = accountId)
                }

            },
            modifier = Modifier
                .heightIn(min = 45.dp)
                .padding(end = 10.dp, start = 10.dp)
        ) {
            SegmentText(it.uiString)
        }
        Spacer(modifier = Modifier.size(15.dp))
        HeaderRow(header = "Open Positions")
        Spacer(modifier = Modifier.size(10.dp))
        if (openTrades.isNotEmpty()) {
            for (index in openTrades.indices) {
                val trade = openTrades[index]
                OpenTradeItem(openTrade = trade)
                if (index < openTrades.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp
                        ),
                        color = AppTheme.colors.onPrimary
                    )
                }
            }
        } else {
            Text(
                text = "This account has no running trades",
                color = AppTheme.colors.textPrimary,
                style = AppTheme.typography.caption
            )
        }


    }
}


@Composable
fun CommentRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            modifier = Modifier
                .height(30.dp)
                .width(30.dp)
                .clip(RoundedCornerShape(5.dp))
                .align(Alignment.CenterVertically),
            model = "https://www.shutterstock.com/shutterstock/photos/1076420156/display_1500/stock-vector-smoking-monkey-mascot-logo-1076420156.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(10.dp))
        Column {
            Text(
                text = "I see the EURUSD up on monday and maybe we go dwin 1.00000",
                style = AppTheme.typography.body,
                color = AppTheme.colors.textPrimary
            )
            Text(
                text = "2 days ago", style = AppTheme.typography.caption,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun AdvancedStatsUi() {
    AccountDetailsCard()
}


@Preview
@Composable
fun CommentRowPreview() {
    CommentRow()
}


@Preview
@Composable
fun TradeHistoryRowPreview() {
    AccountStats(
        metric = DomainAccountMetrics<GraphData>(
            bestTrade = 0.0, wonTradesPercent = 0.0,
            lostTradesPercent = 0.0,
            worstTrade = 0.0,
            dailyGain = 0.0,
            monthlyGain = 0.0,
            daysSinceTradingStarted = 0.0,
            expectancy = 0.0,
            averageWin = 0.0,
            averageLoss = 0.0,
            dailyGrowth = emptyList(),
            monthlyAnalytics = emptyList(),
            balance = 0.0,
            periods = hashMapOf(),
            risk = 0.0,
            profit = 0.0,
            deposits = 0.0
        ),
        tradeHistoryViewModel = getViewModel()
    )
}


@Preview
@Composable
fun TradeHistoryPreview() {
    TradeHistory(traderName = "Carlos", accountId = null)
}

val dataPoints = listOf(
    DataPoint(y = -23.2, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.6, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.4, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 16.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 19.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 23.2, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.6, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.4, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 16.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 19.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 23.2, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.6, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.4, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 16.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 19.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 23.2, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.6, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.4, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 16.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 19.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 23.2, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.6, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.4, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 16.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 19.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 23.2, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.6, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.4, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 16.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 19.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 23.2, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.6, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 22.4, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 16.8, xLabel = "x label", yLabel = "ccc"),
    DataPoint(y = 34.8, xLabel = "x label", yLabel = "ccc")
)


fun tradeHistoryFilters(): List<Filter> {
    return listOf(
        Filter("OverView", true),
        Filter("Trades", false),
        Filter("Advanced Stats", false)
    )
}
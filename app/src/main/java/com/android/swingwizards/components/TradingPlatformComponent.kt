package com.android.swingwizards.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.android.swingwizards.R
import com.android.swingwizards.theme.AppTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import com.android.swingwizards.models.SearchState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import com.android.swingwizards.data.local.entity.TradingPlatformEntity


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TradingPlatformComponent(
    modifier: Modifier,
    tradingPlatForms: List<TradingPlatformEntity>,
    onTradingPlatformClick: (TradingPlatformEntity) -> Unit,
    selectedTradingPlatformEntity: TradingPlatformEntity?,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(top = 5.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = stringResource(id = R.string.trading_platform),
                style = AppTheme.typography.h1,
                color = AppTheme.colors.textPrimary
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(R.string.change_platform),
                style = AppTheme.typography.caption,
                color = AppTheme.colors.textPrimary
            )
            Spacer(modifier = Modifier.height(25.dp))
            SearchSection(onSearch = {}, modifier = Modifier.fillMaxWidth(), state = SearchState())
            Spacer(modifier = Modifier.height(25.dp))
            val tradingPlatforms = tradingPlatForms.groupBy {
                it.name[0]
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                tradingPlatforms.forEach { (initial, tradingPlatformsInitial) ->
                    stickyHeader {
                        Text(
                            text = initial.toString(),
                            color = AppTheme.colors.secondary,
                            style = MaterialTheme.typography.caption
                        )
                    }

                    items(tradingPlatformsInitial, key = { it.name }) { platform ->
                        TradingPlatformItem(
                            onPlatformClick = { onTradingPlatformClick(it) },
                            modifier = Modifier.animateItemPlacement(tween(durationMillis = 500)),
                            tradingPlatform = platform
                        )
                    }
                }

            }
        }
        ButtonSection(
            text = "Continue",
            selectedTradingPlatformEntity = selectedTradingPlatformEntity,
            onButtonClick = { onButtonClick() })
    }
}

@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    state: SearchState,
    onSearch: (String) -> Unit
) {
    Surface(
        color = AppTheme.colors.onPrimary,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "search icon",
                modifier = Modifier.size(
                    24.dp
                ),
                tint = AppTheme.colors.textPrimary
            )

            TextField(
                value = state.query, onValueChange = { value: String ->

                },
                placeholder = {
                    Text(
                        text = "Search Trading platform",
                        style = AppTheme.typography.caption,
                        color = AppTheme.colors.textPrimary
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = AppTheme.colors.secondary
                ),
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = MaterialTheme.typography.caption
            )
        }
    }
}


@Composable
fun ButtonSection(
    modifier: Modifier = Modifier,
    text: String,
    onButtonClick: () -> Unit,
    selectedTradingPlatformEntity: TradingPlatformEntity?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Divider(color = AppTheme.colors.onSurface)
        Box(
            modifier = modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 30.dp
                )
        ) {

            Button(
                onClick = { onButtonClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .alpha(if (selectedTradingPlatformEntity != null) 1f else 0.5f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.secondaryVariant,
                    disabledContainerColor = AppTheme.colors.secondaryVariant

                ),
                enabled = selectedTradingPlatformEntity != null
            ) {

                Text(
                    text = text,
                    style = MaterialTheme.typography.button,
                    color = AppTheme.colors.textPrimary
                )
            }
        }
    }
}


@Preview
@Composable
fun TradingPlatFormPreview() {
    TradingPlatformComponent(
        modifier = Modifier,
        tradingPlatForms = emptyList(),
        onTradingPlatformClick = {},
        selectedTradingPlatformEntity = null
    ) {

    }
}

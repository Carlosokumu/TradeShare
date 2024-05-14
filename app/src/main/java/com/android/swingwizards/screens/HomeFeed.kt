package com.android.swingwizards.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.android.swingwizards.common.EventItem
import com.android.swingwizards.common.TradeShareCardUi
import com.android.swingwizards.components.HomeTopBar
import com.android.swingwizards.enums.HomeFeedContentType
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.viewmodels.EventItem
import com.android.swingwizards.viewmodels.HomeViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun HomeFeed(navController: NavController) {
    val homeViewModel: HomeViewModel = getViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current
    var events by remember { mutableStateOf(listOf<EventItem>()) }
    homeViewModel.eventData.observe(lifecycleOwner) {
        events = it.data

    }
    val username = remember { mutableStateOf("Username") }

    homeViewModel.username.observe((lifecycleOwner)) { currentUsername ->
        username.value = currentUsername
    }

    Scaffold(
        topBar = {
            HomeTopBar(navController = navController, username = username.value)
        },
        containerColor = AppTheme.colors.background,
        floatingActionButton = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable { }
                    .background(AppTheme.colors.secondaryVariant)
                    .border(1.dp, AppTheme.colors.secondaryVariant, CircleShape)
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Tweet",
                        tint = Color.White
                    )
                }

            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(AppTheme.colors.background)
        ) {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppTheme.colors.secondaryVariant),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TradeShareCardUi()
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp)
                ) {
                    FeedContentType()
                }

            }
            itemsIndexed(events) { index, event ->
                EventItem(event)
                if (index < events.lastIndex)
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
}


data class Account(val name: String, val gain: String)


@Composable
fun Slide(account: Account, horizontal: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(8.dp)
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(AppTheme.colors.onPrimary)
            .clickable { }
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(55.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(AppTheme.colors.onSurface),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    model = "https://avatars.githubusercontent.com/u/38771353?v=4",
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Column {
                Text(account.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(account.name, fontSize = 12.sp, color = Color.Gray)
            }
        }
        if (horizontal) {
            Spacer(modifier = Modifier.width(10.dp))
        }
        Column(horizontalAlignment = Alignment.End) {
            Text("+200$")
            Spacer(modifier = Modifier.height(2.dp))
            val percent = (100.0)
            val textColor = if (percent >= 0) Green else Red
            Text(text = "${percent}%", color = textColor)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedContentType() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(HomeFeedContentType.values().first().option) }
    Box {
        ElevatedFilterChip(
            selected = true,
            modifier = Modifier.padding(all = 10.dp),
            onClick = { expanded = !expanded },
            label = { Text(selectedOption) },
            colors = FilterChipDefaults.elevatedFilterChipColors(
                containerColor = AppTheme.colors.onPrimary,
                selectedContainerColor = AppTheme.colors.onPrimary,
                selectedLabelColor = AppTheme.colors.textPrimary,
                labelColor = AppTheme.colors.textPrimary,
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Localized Description",
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                    tint = AppTheme.colors.onSurface
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            modifier = Modifier.background(AppTheme.colors.onPrimary),
            offset = DpOffset(x = 200.dp, y = 0.dp),
            onDismissRequest = { expanded = false }
        ) {
            HomeFeedContentType.values().forEach { option ->
                DropdownMenuItem(text = { Text(option.option) }, onClick = {
                    selectedOption = option.option
                    expanded = false
                })
            }
        }
    }
}









package com.android.swingwizards.screens

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.android.swingwizards.R
import com.android.swingwizards.theme.AppTheme
import com.android.swingwizards.viewmodels.ProfileScreenViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun ProfileScreen(navController: NavController) {
    val profileScreenViewModel: ProfileScreenViewModel = getViewModel()
    val username = remember { mutableStateOf("Username") }
    val lifecycleOwner = LocalLifecycleOwner.current


    profileScreenViewModel.getUsername()
    profileScreenViewModel.username.observe((lifecycleOwner)) { currentusername ->
        username.value = currentusername
    }

    Scaffold(
        topBar = {
            ProfileTopAppBar(username = username.value)
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(AppTheme.colors.background)
        ) {
            Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(AppTheme.colors.background),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Spacer(modifier = Modifier.size(10.dp))
                    ConnectCard(navController = navController)
                    Spacer(modifier = Modifier.size(10.dp))

                }
                Spacer(modifier = Modifier.size(10.dp))
                CustomDivider(text = "My Account")
                AccountItem(icon = R.drawable.ic_account_icon, title = "Personal Information") {

                }
                AccountItem(icon = R.drawable.ic_cards, title = "Trading Accounts") {
                    navController.navigate("trading_accounts")
                }
                AccountItem(icon = R.drawable.ic_chart, title = "Trading History") {
                    navController.navigate("trade-history")
                }
                AccountItem(icon = R.drawable.pie_chart, title = "Portfolio") {

                }
                AccountItem(icon = R.drawable.wallet, title = "Wallet") {

                }
                AccountItem(icon = R.drawable.ic_crowd, title = "Campaign") {
                }
                Spacer(modifier = Modifier.size(10.dp))
                CustomDivider(text = "About TradeShare")
                AccountItem(icon = R.drawable.about, title = "About TradeShare") {
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}


@Composable
fun ProfileTopAppBar(username: String = "") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background)
            .padding(all = 5.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    modifier = Modifier
                        .height(45.dp)
                        .width(45.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterVertically),
                    model = "https://www.shutterstock.com/shutterstock/photos/1076420156/display_1500/stock-vector-smoking-monkey-mascot-logo-1076420156.jpg",
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = username,
                        modifier = Modifier.padding(horizontal = 5.dp),
                        style = AppTheme.typography.subtitle,
                        color = AppTheme.colors.textPrimary
                    )
                }
            }


            TextButton(onClick = { }) {
                Text(
                    text = "Edit",
                    style = AppTheme.typography.subtitle,
                    color = AppTheme.colors.textPrimary,
                    modifier = Modifier.drawBehind {
                        val strokeWidthPx = 2.dp.toPx()
                        val verticalOffset = size.height - 2.sp.toPx()
                        drawLine(
                            color = Color.DarkGray,
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    }
                )
            }

        }

    }

}


@Composable
fun ConnectCard(navController: NavController) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(3.dp),
        backgroundColor = AppTheme.colors.onPrimary,
        elevation = 10.dp
    ) {
        Row(modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    text = "Connect Account",
                    modifier = Modifier.padding(horizontal = 5.dp),
                    style = AppTheme.typography.subtitle,
                    color = AppTheme.colors.textPrimary
                )
                Text(
                    text = "Connect your existing trading account",
                    modifier = Modifier.padding(horizontal = 5.dp),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.textPrimary
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate("connect")
                    }
                    .background(AppTheme.colors.secondaryVariant)
                    .border(1.dp, AppTheme.colors.secondaryVariant, CircleShape)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable._ic_add),
                    contentDescription = "Action",
                    modifier = Modifier.size(30.dp),
                    tint = AppTheme.colors.textPrimary
                )

            }
        }
    }
}


@Composable
fun AccountDetailsSection() {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = "Trading Accounts",
            modifier = Modifier.padding(horizontal = 5.dp),
            style = AppTheme.typography.button,
            color = AppTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.size(8.dp))
        Divider(color = AppTheme.colors.onSurface)
    }
}


@Composable
fun CustomDivider(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = text, style = AppTheme.typography.subtitle,
            color = AppTheme.colors.textPrimary
        )

        Spacer(modifier = modifier.size(16.dp))

        Divider(
            modifier = Modifier.weight(1f),
            color = AppTheme.colors.onPrimary
        )

    }

}


@Composable
fun AccountItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String,
    onItemClick: () -> Unit
) {

    Surface(
        onClick = { onItemClick() },
        shape = RoundedCornerShape(5.dp),
        color = AppTheme.colors.background,
        modifier = modifier
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                horizontal = 15.dp,
                vertical = 15.dp
            )
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = AppTheme.colors.textPrimary
            )

            Text(
                text = title, style = AppTheme.typography.caption,
                color = AppTheme.colors.textPrimary,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.chevron_right),
                contentDescription = null,
                modifier = Modifier.size(15.dp),
                tint = AppTheme.colors.textPrimary
            )
        }

    }

}


@Preview
@Composable
fun AccountDetailsSectionPreview() {
    AccountDetailsSection()
}


@Preview
@Composable
fun ConnectCardPreview() {
    ConnectCard(navController = rememberNavController())
}

@Preview
@Composable
fun ProfileTopPreview() {
    ProfileTopAppBar()
}
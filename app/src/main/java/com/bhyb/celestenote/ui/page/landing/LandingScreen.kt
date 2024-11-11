package com.bhyb.celestenote.ui.page.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bhyb.celestenote.R
import com.bhyb.celestenote.route.ROUTE_BOTTOM_NAV
import com.bhyb.celestenote.route.ROUTE_LANDING_SCREEN
import kotlinx.coroutines.delay

@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.35f))

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "app logo",
            colorFilter = ColorFilter.tint(colorResource(id = R.color.bottom_navbar_color)),
            alignment = Alignment.Center
        )
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))
    }

    //Jump to the home page after a delay
    LaunchedEffect(key1 = true){
        delay(2000)
        navController.navigate(ROUTE_BOTTOM_NAV){
            popUpTo(ROUTE_LANDING_SCREEN){
                inclusive = true
            }
        }
    }
}
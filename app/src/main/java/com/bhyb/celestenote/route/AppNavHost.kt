package com.bhyb.celestenote.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bhyb.celestenote.compose.bottomnavbar.BottomNav
import com.bhyb.celestenote.ui.page.landing.LandingScreen

const val ROUTE_LANDING_SCREEN = "landing_screen"
const val ROUTE_BOTTOM_NAV = "bottom_nav"

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_LANDING_SCREEN
    ){
        composable(ROUTE_LANDING_SCREEN) {
            LandingScreen(modifier,navController)
        }

        composable(ROUTE_BOTTOM_NAV) {
            BottomNav()
        }
    }
}
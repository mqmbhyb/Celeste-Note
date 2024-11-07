package com.bhyb.celestenote.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bhyb.celestenote.ui.page.add.Add
import com.bhyb.celestenote.ui.page.home.Home
import com.bhyb.celestenote.ui.page.my.My

private const val ROUTE_HOME = "home"
private const val ROUTE_ADD = "add"
private const val ROUTE_MY = "my"

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_HOME
    ){
        composable(ROUTE_HOME){
            Home()
        }

        composable(ROUTE_ADD){
            Add()
        }

        composable(ROUTE_MY){
            My()
        }
    }
}
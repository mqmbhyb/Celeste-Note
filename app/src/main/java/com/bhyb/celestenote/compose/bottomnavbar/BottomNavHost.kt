package com.bhyb.celestenote.compose.bottomnavbar

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bhyb.celestenote.ui.page.add.Add
import com.bhyb.celestenote.ui.page.my.My
import com.bhyb.celestenote.ui.page.note.Note
import com.bhyb.celestenote.ui.page.note.drawer.DrawerScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun BottomNavHost(
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: DrawerScreen,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavBarScreen.Note.route
    ){
        composable(BottomNavBarScreen.Note.route){
            Note(drawerState, scope, selectedItem)
        }

        composable(BottomNavBarScreen.Add.route){
            Add()
        }

        composable(BottomNavBarScreen.My.route){
            My()
        }
    }
}
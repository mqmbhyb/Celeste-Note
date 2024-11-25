package com.bhyb.celestenote.ui.component.bottomnavbar

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bhyb.celestenote.ui.screen.add.AddScreen
import com.bhyb.celestenote.ui.screen.my.MyScreen
import com.bhyb.celestenote.ui.screen.note.NoteScreen
import com.bhyb.celestenote.ui.screen.note.drawer.DrawerScreen
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
            NoteScreen(drawerState, scope, selectedItem)
        }

        composable(BottomNavBarScreen.Add.route){
            AddScreen()
        }

        composable(BottomNavBarScreen.My.route){
            MyScreen()
        }
    }
}
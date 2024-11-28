package com.bhyb.celestenote.ui.component.bottomnavbar

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bhyb.celestenote.ui.screen.add.AddNoteScreen
import com.bhyb.celestenote.ui.screen.add.AddScreen
import com.bhyb.celestenote.ui.screen.my.MyScreen
import com.bhyb.celestenote.ui.screen.note.NoteScreen
import com.bhyb.celestenote.ui.screen.note.drawer.DrawerScreen
import kotlinx.coroutines.CoroutineScope

val slideIn = slideInHorizontally(initialOffsetX = { it })
val slideOut = slideOutHorizontally(targetOffsetX = { it })

const val ROUTE_ADD_NOTE_SCREEN = "add_note_screen"

@Composable
fun BottomNavHost(
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: DrawerScreen,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavBarScreen.Note.route
    ){
        composable(BottomNavBarScreen.Note.route){
            NoteScreen(
                drawerState,
                scope,
                selectedItem,
                onAddNoteClicked = {
                    navController.navigate(ROUTE_ADD_NOTE_SCREEN)
                },
            )
        }

        composable(BottomNavBarScreen.Add.route){
            AddScreen(
                onAddNoteClicked = {
                    navController.navigate(ROUTE_ADD_NOTE_SCREEN)
                }
            )
        }

        composable(BottomNavBarScreen.My.route){
            MyScreen()
        }

        composable(
            ROUTE_ADD_NOTE_SCREEN,
            enterTransition = { slideIn },
            exitTransition = { slideOut },
            popEnterTransition = { slideIn },
            popExitTransition = { slideOut }
            ) {
            AddNoteScreen(navController)
        }
    }
}
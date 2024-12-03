package com.bhyb.celestenote.ui.component.bottomnavbar

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bhyb.celestenote.ui.screen.explore.ExploreScreen
import com.bhyb.celestenote.ui.screen.my.MyScreen
import com.bhyb.celestenote.ui.screen.note.NoteScreen
import com.bhyb.celestenote.ui.screen.note.SearchScreen
import com.bhyb.celestenote.ui.screen.note.addeditnote.AddEditNoteScreen
import com.bhyb.celestenote.ui.screen.note.drawer.DrawerScreen
import kotlinx.coroutines.CoroutineScope

val slideIn = slideInHorizontally(initialOffsetX = { it })
val slideOut = slideOutHorizontally(targetOffsetX = { it })

const val ROUTE_ADD_Edit_NOTE_SCREEN = "add_edit_note_screen"
const val ROUTE_SEARCH_SCREEN = "search_screen"

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
    ) {
        composable(BottomNavBarScreen.Note.route) {
            NoteScreen(
                drawerState,
                scope,
                selectedItem,
                onAddNoteClicked = {
                    navController.navigate(ROUTE_ADD_Edit_NOTE_SCREEN)
                },
                onSearchClicked = {
                    navController.navigate(ROUTE_SEARCH_SCREEN)
                },
            )
        }

        composable(BottomNavBarScreen.Explore.route) {
            ExploreScreen()
        }

        composable(BottomNavBarScreen.My.route) {
            MyScreen()
        }

        composable(
            route = "$ROUTE_ADD_Edit_NOTE_SCREEN?noteId={noteId}",
            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            ),
            enterTransition = { slideIn },
            exitTransition = { slideOut },
            popEnterTransition = { slideIn },
            popExitTransition = { slideOut }
        ) {
            AddEditNoteScreen(navController)
        }

        composable(
            ROUTE_SEARCH_SCREEN,
            enterTransition = { slideIn },
            exitTransition = { slideOut },
            popEnterTransition = { slideIn },
            popExitTransition = { slideOut }
        ) {
            SearchScreen()
        }
    }
}
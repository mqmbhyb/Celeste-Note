package com.bhyb.celestenote.ui.component.bottomnavbar

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bhyb.celestenote.R
import com.bhyb.celestenote.domain.model.Category
import com.bhyb.celestenote.ui.screen.note.drawer.DrawerContent
import com.bhyb.celestenote.ui.screen.note.drawer.DrawerScreen
import com.bhyb.celestenote.ui.screen.note.drawer.DrawerViewModel
import kotlinx.coroutines.launch

fun Category.toDrawerScreen(): DrawerScreen = object : DrawerScreen(this.title, R.drawable.ic_document, this.id) {}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNav() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val viewModel: DrawerViewModel = hiltViewModel()
    val customCategories by viewModel.customizedCategories.collectAsStateWithLifecycle(emptyList())
    val customDrawerItems = remember(customCategories) {
        customCategories.map { it.toDrawerScreen() }
    }
    var selectedItem by remember { mutableStateOf<DrawerScreen>(DrawerScreen.AllNote) }
    val currentRoute = remember(navController) {
        mutableStateOf(navController.currentBackStackEntry?.destination?.route ?: "")
    }
    // 监听导航控制器中的路由变化
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route.toString()
        }
    }

    val drawerContent: @Composable () -> Unit = {
        DrawerContent(
            selectedItem = selectedItem,
            onItemSelected = {
                selectedItem = it;
                scope.launch { drawerState.close() }
            },
            customDrawerItems = customDrawerItems
        )
    }

    ModalNavigationDrawer(
        drawerContent = drawerContent,
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen //仅在抽屉打开时允许手势开关抽屉
    ){
        Scaffold(
            bottomBar = {
                if (currentRoute.value in listOf(BottomNavBarScreen.Note.route, BottomNavBarScreen.Explore.route, BottomNavBarScreen.My.route)) {
                    BottomBar(navController = navController)
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color.White,
                                colorResource(id = R.color.screen_background_color)
                            )
                        )
                    )
                    .fillMaxSize()
                    .padding(it)
            ) {
                BottomNavHost(drawerState, scope, selectedItem, navController)
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val screens = listOf(
        BottomNavBarScreen.Note,
        BottomNavBarScreen.Explore,
        BottomNavBarScreen.My
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 12.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen, currentDestination, navController
            )
        }
    }
}

@Composable
fun AddItem(
    screen: BottomNavBarScreen,
    currentDestination: NavDestination?,
    navController: NavController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val background =
        if (selected) colorResource(R.color.bottom_navbar_color) else Color.Transparent

    val contentColor =
        if (selected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "icon",
                tint = contentColor
            )

            AnimatedVisibility(visible = selected) {
                Text(text = stringResource(screen.title), color = contentColor)
            }
        }
    }
}
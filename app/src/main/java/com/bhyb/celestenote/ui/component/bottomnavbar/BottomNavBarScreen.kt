package com.bhyb.celestenote.ui.component.bottomnavbar

import com.bhyb.celestenote.R

sealed class BottomNavBarScreen(
    val route:String,
    val title: Int,
    val icon:Int
) {
    object Note: BottomNavBarScreen(
        route = "note",
        title = R.string.bottombar_title_note,
        icon = R.drawable.ic_bottom_note
    )

    object Explore: BottomNavBarScreen(
        route = "explore",
        title = R.string.bottombar_title_explore,
        icon = R.drawable.ic_bottom_explore
    )

    object My: BottomNavBarScreen(
        route = "my",
        title = R.string.bottombar_title_my,
        icon = R.drawable.ic_bottom_my
    )
}
package com.bhyb.celestenote.ui.component.bottomnavbar

import com.bhyb.celestenote.R

sealed class BottomNavBarScreen(
    val route:String,
    val title: Int,
    val icon:Int,
    val icon_focused:Int
) {
    object Note: BottomNavBarScreen(
        route = "note",
        title = R.string.bottombar_title_note,
        icon = R.drawable.ic_bottom_note,
        icon_focused = R.drawable.ic_bottom_note_focused
    )

    object Add: BottomNavBarScreen(
        route = "add",
        title = R.string.bottombar_title_add,
        icon = R.drawable.ic_bottom_add,
        icon_focused = R.drawable.ic_bottom_add_focused
    )

    object My: BottomNavBarScreen(
        route = "my",
        title = R.string.bottombar_title_my,
        icon = R.drawable.ic_bottom_my,
        icon_focused = R.drawable.ic_bottom_my_focused
    )
}
package com.bhyb.celestenote.ui.screen.note.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DrawerScreen(val title: String, val icon: ImageVector) {
    object AllNote : DrawerScreen("全部笔记", Icons.Filled.Menu)
    object Uncategorized : DrawerScreen("未分类笔记", Icons.Filled.Clear)
    object LockNote : DrawerScreen("已加密笔记", Icons.Filled.Lock)
}

val drawerItems = listOf(
    DrawerScreen.AllNote,
    DrawerScreen.Uncategorized,
    DrawerScreen.LockNote
)
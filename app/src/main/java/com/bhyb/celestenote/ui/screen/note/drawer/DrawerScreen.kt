package com.bhyb.celestenote.ui.screen.note.drawer

import com.bhyb.celestenote.R

open class DrawerScreen(val title: String, val icon: Int, val categoryId: Int? = null) {
    object AllNote : DrawerScreen("全部笔记", R.drawable.ic_all_notes)
    object Uncategorized : DrawerScreen("未分类笔记", R.drawable.ic_unsorted_notes)
    object LockNote : DrawerScreen("已加密笔记", R.drawable.ic_encrypted_notes)
}

val drawerItems = listOf(
    DrawerScreen.AllNote,
    DrawerScreen.Uncategorized,
    DrawerScreen.LockNote
)
package com.bhyb.celestenote.ui.screen.note.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bhyb.celestenote.R

@Composable
fun DrawerContent(
    selectedItem: DrawerScreen,
    onItemSelected: (DrawerScreen) -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        modifier = Modifier
            .width(300.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(id = R.string.note_category_title),
                color = colorResource(id = R.color.bottom_navbar_color),
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
            )
        }

        //TODO 分类的新建与删除

        drawerItems.forEach { item ->
            NavigationDrawerItem(
                label = { Text(item.title) },
                icon = { Icon(item.icon, contentDescription = null) },
                selected = item == selectedItem,
                onClick = { onItemSelected(item) },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
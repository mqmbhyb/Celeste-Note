package com.bhyb.celestenote.ui.page.note

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.bhyb.celestenote.R
import com.bhyb.celestenote.ui.page.note.drawer.DrawerScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: DrawerScreen,
    modifier: Modifier = Modifier
) {
    // 测试数据
    val text1 = """
        床前明月光，疑是地上霜。
        举头望明月，低头思故乡。
    """.trimIndent()
    val text2 = """
        移舟泊烟渚，日暮客愁新。
        野旷天低树，江清月近人。
    """.trimIndent()
    val text3 = """
        大漠沙如雪，燕山月似钩。
        何当金络脑，快走踏清秋。
    """.trimIndent()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Row(
                        modifier = modifier
                            .clickable {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                }
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedItem.title,
                            modifier = modifier
                                .fillMaxHeight()
                                .wrapContentSize(Alignment.Center)
                            )
                        Icon(
                            Icons.Filled.PlayArrow,
                            contentDescription = "展开分类",
                            tint = colorResource(id = R.color.top_bar_icon_color),
                            modifier = modifier
                                .padding(top = 4.dp)
                                .size(18.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(Icons.Filled.Search, null)
                    }
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(Icons.Filled.Add, null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = modifier.padding(paddingValues)) {
            when (selectedItem) {
                DrawerScreen.AllNote -> NoteItem(modifier, text1)
                DrawerScreen.Uncategorized -> NoteItem(modifier, text2)
                DrawerScreen.LockNote -> NoteItem(modifier, text3)
            }
        }
    }
}


@Composable
fun NoteItem(
    modifier: Modifier,
    text: String
) {
    Column(
        modifier = modifier
            .background(colorResource(id = R.color.screen_background_color))
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = text,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
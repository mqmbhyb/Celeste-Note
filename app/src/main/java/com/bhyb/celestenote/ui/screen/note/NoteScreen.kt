package com.bhyb.celestenote.ui.screen.note

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bhyb.celestenote.R
import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.ui.screen.note.drawer.DrawerScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: DrawerScreen,
    onSearchClicked: () -> Unit,
    onAddNoteClicked: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val notes by viewModel.notes.collectAsStateWithLifecycle(emptyList())

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
                                .size(18.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSearchClicked) {
                        Icon(Icons.Filled.Search, "Search")
                    }
                    IconButton(onClick = onAddNoteClicked) {
                        Icon(Icons.Filled.Add, "新建笔记")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colorResource(id = R.color.screen_background_color))
        ) {
            when (selectedItem) {
                DrawerScreen.AllNote -> NotesClassificationDisplay(notes)
                DrawerScreen.Uncategorized -> NotesClassificationDisplay(notes.filter { false })
                DrawerScreen.LockNote -> NotesClassificationDisplay(notes.filter { false })
            }
        }
    }
}


@Composable
fun NotesClassificationDisplay(
    notes: List<Note>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(notes) { note ->
            NoteItem(
                note = note,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // TODO 短按打开笔记详情 长按弹出编辑项
                    }
            )
        }
    }
}
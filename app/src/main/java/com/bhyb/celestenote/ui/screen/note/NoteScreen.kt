package com.bhyb.celestenote.ui.screen.note

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bhyb.celestenote.R
import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.ui.component.BiometricAuthenticationHelper
import com.bhyb.celestenote.ui.component.GridItem
import com.bhyb.celestenote.ui.component.GridSection
import com.bhyb.celestenote.ui.component.PassParametersToast
import com.bhyb.celestenote.ui.component.ShowBottomSheet
import com.bhyb.celestenote.ui.component.bottomnavbar.ROUTE_ADD_EDIT_NOTE_SCREEN
import com.bhyb.celestenote.ui.screen.note.drawer.DrawerScreen
import com.bhyb.celestenote.ui.screen.note.drawer.DrawerViewModel
import com.bhyb.celestenote.ui.screen.note.noteoperation.NoteOperation
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
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel(),
    drawerViewModel: DrawerViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val toastForClickTool = {
        PassParametersToast.show(context, R.string.functional_development)
    }

    val notes by viewModel.notes.collectAsStateWithLifecycle(emptyList())
    val notesByIsLock by viewModel.notesByIsLock.collectAsStateWithLifecycle(emptyList())

    var showModalBottomSheet by remember { mutableStateOf(false) }
    var sheetContent: @Composable (() -> Unit)? by remember { mutableStateOf(null) }
    val onDismissRequest = {
        showModalBottomSheet = false
        sheetContent = null
    }
    if (showModalBottomSheet && sheetContent != null) {
        ShowBottomSheet(
            sheetContent = sheetContent!!,
            onDismissRequest = onDismissRequest
        )
    }

    val onNoteItemLongPress: (Note, (action: () -> Unit) -> Unit) -> Unit = { note, biometricHelper ->
        sheetContent = {
            note.id?.let {
                NoteOperation(it, onDismissRequest, biometricHelper)
            }
        }
        showModalBottomSheet = true
    }

    LaunchedEffect(selectedItem) {
        if (selectedItem.categoryId != null) {
            drawerViewModel.onGetNoteByCategory(selectedItem.categoryId)
        }
    }

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
                    IconButton(
                        onClick = {
                            sheetContent = {
                                GridSection(
                                    title = "常用工具",
                                    imageSize = 40.dp,
                                    items = listOf(
                                        GridItem(
                                            R.drawable.ic_tool_lockbox,
                                            "密码箱"
                                        ) { toastForClickTool() },
                                        GridItem(
                                            R.drawable.ic_tool_todo,
                                            "待办"
                                        ) { toastForClickTool() },
                                        GridItem(
                                            R.drawable.ic_tool_accounting,
                                            "记账"
                                        ) { toastForClickTool() },
                                        GridItem(
                                            R.drawable.ic_tool_mindmap,
                                            "思维导图"
                                        ) { toastForClickTool() }
                                    )
                                )
                            }
                            showModalBottomSheet = true
                        }
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_tool),
                            "工具",
                            modifier = Modifier.size(25.dp)
                        )
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
                DrawerScreen.AllNote -> NotesClassificationDisplay(notes, onNoteItemLongPress, navController, viewModel, context)

                DrawerScreen.Uncategorized -> {
                    drawerViewModel.onGetNoteByCategory(0)
                    val uncategorizedNotes by drawerViewModel.notesByCategory.collectAsStateWithLifecycle(
                        emptyList()
                    )
                    NotesClassificationDisplay(uncategorizedNotes, onNoteItemLongPress, navController, viewModel, context)
                }

                DrawerScreen.LockNote -> NotesClassificationDisplay(notesByIsLock, onNoteItemLongPress, navController, viewModel, context)

                else -> {
                    val categorizedNotes by drawerViewModel.notesByCategory.collectAsStateWithLifecycle(
                        emptyList()
                    )
                    NotesClassificationDisplay(categorizedNotes, onNoteItemLongPress, navController, viewModel, context)
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesClassificationDisplay(
    notes: List<Note>,
    onNoteItemLongPress: (Note, (action: () -> Unit) -> Unit) -> Unit,
    navController: NavController,
    viewModel: NoteViewModel,
    context: Context,
    modifier: Modifier = Modifier
) {
    val hapticFeedback = LocalHapticFeedback.current

    val biometricHelper : (action: () -> Unit) -> Unit =  { action ->
        BiometricAuthenticationHelper(
            activity = context as FragmentActivity,
            callback = object : BiometricAuthenticationHelper.Callback {
                override fun onSuccess(message: String) {
                    action()
                }

                override fun onError(message: String) {
                    PassParametersToast.show(context, message)
                }

                override fun onFailed(message: String) {
                    PassParametersToast.show(context, message)
                }
            }).authenticate()
    }

    if (notes.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("没有笔记")
        }
    } else {
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            itemsIndexed(notes) { index, note ->
                LaunchedEffect(note.isLock) {
                    note.id?.let { viewModel.onGetNote(it) }
                }

                NoteItem(
                    note = note,
                    isLastItem = index == notes.lastIndex,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = {
                                if (viewModel.isLockNote.value) {
                                    biometricHelper { navController.navigate(ROUTE_ADD_EDIT_NOTE_SCREEN + "?noteId=${note.id}") }
                                } else {
                                    navController.navigate(
                                        ROUTE_ADD_EDIT_NOTE_SCREEN + "?noteId=${note.id}"
                                    )
                                }
                            },
                            onLongClick = {
                                onNoteItemLongPress(note, biometricHelper)
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            }
                        )
                )
            }
        }
    }
}
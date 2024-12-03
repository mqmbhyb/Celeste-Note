package com.bhyb.celestenote.ui.screen.note.addeditnote

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhyb.celestenote.ui.component.PassParametersToast
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    var showMoreOptions by remember { mutableStateOf(false) }
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val context = LocalContext.current
    val noteTitle by viewModel.noteTitle
    val noteContent by viewModel.noteContent
    val isNoteSaved by viewModel.isNoteSaved.collectAsState()
    val isSaveEnabled by remember {
        derivedStateOf {
            noteTitle.text.isNotBlank() || noteContent.text.isNotBlank()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditNoteViewModel.ClickEvent.ShowToast -> {
                    PassParametersToast.show(context, message = event.message)
                }
                is AddEditNoteViewModel.ClickEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "ArrowBack")
                    }
                },
                title = { /* 无标题 */ },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(AddEditNoteEvent.SaveNote)
                            //todo 调用修改笔记的用例更新updateTime
                        },
                        enabled = isSaveEnabled
                    ) {
                        Icon(Icons.Filled.Check, "保存笔记")
                    }
                    IconButton(
                        onClick = { showMoreOptions = !showMoreOptions },
                        // enabled = isNoteSaved
                    ) {
                        Icon(Icons.Filled.MoreVert, "更多")
                    }
                    DropdownMenu(
                        expanded = showMoreOptions,
                        onDismissRequest = { showMoreOptions = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text("加密") },
                            leadingIcon = { Icon(Icons.Default.Lock, "") },
                            onClick = {
                                showMoreOptions = false
                            },
                            enabled = isNoteSaved
                        )
                        DropdownMenuItem(
                            text = { Text("上传") },
                            leadingIcon = { Icon(Icons.Default.Share, "") },
                            onClick = {
                                showMoreOptions = false
                            },
                            enabled = isNoteSaved
                        )
                        DropdownMenuItem(
                            text = { Text("删除", color = MaterialTheme.colorScheme.error) },
                            leadingIcon = { Icon(Icons.Default.Delete, "", tint = MaterialTheme.colorScheme.error) },
                            onClick = {
                                showMoreOptions = false
                            },
                            enabled = isNoteSaved
                        )
                    }
                }
            )
        }
    ) {innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(16.dp)
        ) {
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

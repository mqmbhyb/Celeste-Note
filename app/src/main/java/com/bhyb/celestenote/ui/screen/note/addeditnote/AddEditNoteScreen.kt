package com.bhyb.celestenote.ui.screen.note.addeditnote

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhyb.celestenote.R
import com.bhyb.celestenote.ui.component.BiometricAuthenticationHelper
import com.bhyb.celestenote.ui.component.PassParametersToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var showMoreOptions by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    var titleFocusState by remember { mutableStateOf(false) }
    var contentFocusState by remember { mutableStateOf(false) }

    val isGetFocus by remember {
        derivedStateOf {
            titleFocusState || contentFocusState
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.ClickEvent.ShowToast -> {
                    PassParametersToast.show(context, message = event.message)
                }

                is AddEditNoteViewModel.ClickEvent.SaveNote -> {
                    focusManager.clearFocus()
                }

                is AddEditNoteViewModel.ClickEvent.UpdateNote -> {
                    focusManager.clearFocus()
                }
            }
        }
    }

    val handleBack = {
        if (viewModel.isModified && viewModel.isCreateNote && isGetFocus) {
            viewModel.onEvent(AddEditNoteEvent.SaveNote)
            navController.navigateUp()
        } else if (viewModel.contentIncOrUnchangedOrTitleInc) {
            viewModel.onEvent(AddEditNoteEvent.UpdateNote)
            navController.navigateUp()
        } else if (viewModel.contentDecOrTitleDec) {
            showConfirmDialog = true
        } else {
            navController.navigateUp()
        }
    }

    // 判断保存还是修改
    val saveOrUpdate = {
        if (viewModel.isCreateNote) {
            viewModel.onEvent(AddEditNoteEvent.SaveNote)
        } else {
            viewModel.onEvent(AddEditNoteEvent.UpdateNote)
        }
    }

    val biometricHelper = remember {
        BiometricAuthenticationHelper(
            activity = context as FragmentActivity,
            callback = object : BiometricAuthenticationHelper.Callback {
                override fun onSuccess(message: String) {
                    viewModel.noteIsLock.value.not()
                    viewModel.onEvent(AddEditNoteEvent.UpdateNote)
                    PassParametersToast.show(context, message)
                }

                override fun onError(message: String) {
                    PassParametersToast.show(context, message)
                }

                override fun onFailed(message: String) {
                    PassParametersToast.show(context, message)
                }
            })
    }

    val onLockClick = {
        showMoreOptions = false
        biometricHelper.authenticate()
    }

    // 监听返回事件
    BackHandler {
        scope.launch {
            handleBack()
        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("你尚未保存此次修改") },
            text = { Text("是否保存对此笔记的修改?") },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                    if (viewModel.isBlankNote) {
                        keyboardController?.hide()
                        viewModel.onEvent(AddEditNoteEvent.DeleteNotes)
                    } else {
                        saveOrUpdate()
                    }
                    navController.navigateUp()
                }) {
                    Text("保存")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                    navController.navigateUp()
                }) {
                    Text("不保存")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                navigationIcon = {
                    IconButton(onClick = { handleBack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "ArrowBack")
                    }
                },
                title = { /* 无标题 */ },
                actions = {
                    if (isGetFocus) {
                        IconButton(
                            onClick = saveOrUpdate,
                            enabled = !viewModel.isBlankNote
                        ) {
                            Icon(Icons.Filled.Check, "保存笔记")
                        }
                    } else {
                        IconButton(
                            onClick = { showMoreOptions = !showMoreOptions },
                            enabled = !viewModel.isBlankNote
                        ) {
                            Icon(Icons.Filled.MoreVert, "更多")
                        }
                        DropdownMenu(
                            expanded = showMoreOptions,
                            onDismissRequest = { showMoreOptions = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(if (viewModel.noteIsLock.value.boolean) "取消加密" else "加密")
                                },
                                leadingIcon = {
                                    Icon(
                                        if (viewModel.noteIsLock.value.boolean) painterResource(R.drawable.ic_unlock2)
                                        else painterResource(R.drawable.ic_lock2),
                                        contentDescription = null,
                                        modifier = Modifier.size(25.dp)
                                    )
                                },
                                onClick = { onLockClick() }
                            )
                            DropdownMenuItem(
                                text = { Text("上传") },
                                leadingIcon = { Icon(Icons.Default.Share, "") },
                                onClick = {
                                    showMoreOptions = false
                                    viewModel.noteIsUpload.value.boolean = true
                                    viewModel.onEvent(AddEditNoteEvent.UpdateNote)
                                },
                                enabled = !viewModel.noteIsUpload.value.boolean
                            )
                            DropdownMenuItem(
                                text = { Text("删除", color = MaterialTheme.colorScheme.error) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Delete,
                                        "",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                },
                                onClick = {
                                    if (!viewModel.noteIsDelete.value.boolean) {
                                        showMoreOptions = false
                                        viewModel.noteIsDelete.value.boolean = true
                                        viewModel.onEvent(AddEditNoteEvent.UpdateNote)
                                    }
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
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
                    titleFocusState = it.isFocused
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
                    contentFocusState = it.isFocused
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

package com.bhyb.celestenote.ui.screen.note.noteoperation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhyb.celestenote.R
import com.bhyb.celestenote.ui.component.GridItem
import com.bhyb.celestenote.ui.component.GridSection
import com.bhyb.celestenote.ui.component.ShowBottomSheet

@Composable
fun NoteOperation(
    noteId: Int,
    closeBottomSheet: () -> Unit,
    viewModel: NoteOperationViewModel = hiltViewModel()
) {
    val isUpload by viewModel.isUpload.collectAsState(initial = false)
    val isLock by viewModel.isLock.collectAsState(initial = false)

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

    LaunchedEffect(key1 = true) {
        viewModel.onGetNote(noteId)
        viewModel.getCategories()
    }

    //todo加密状态更改要验证
    val (icon, text) = if (isLock)
        R.drawable.ic_unlock1 to "取消加密"
    else
        R.drawable.ic_lock1 to "加密"

    Column {
        GridSection(
            imageSize = 25.dp,
            items = listOf(
                GridItem(R.drawable.ic_move, "移动") {
                    sheetContent = {
                        CategorySelectionSheetContent(
                            closeBottomSheet = closeBottomSheet,
                            noteId = noteId,
                            onDismissRequest = onDismissRequest,
                            viewModel = viewModel
                        )
                    }
                    showModalBottomSheet = true
                },
                GridItem(icon, text) {
                    viewModel.onLockNote(noteId)
                    closeBottomSheet()
                },
                GridItem(R.drawable.ic_upload, "上传", enabled = !isUpload) {
                    viewModel.onUploadNote(noteId)
                    //todo 网络请求
                    closeBottomSheet()
                },
                GridItem(R.drawable.ic_trash, "删除", MaterialTheme.colorScheme.error) {
                    viewModel.onDeleteNoteToRecycle(noteId)
                    closeBottomSheet()
                }
            ),
            shapeClip = 0.dp
        )
        Button(
            onClick = closeBottomSheet,
            colors = ButtonDefaults.buttonColors(colorResource(R.color.bottom_navbar_color), Color.White),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        ) {
            Text("取消")
        }
    }
}
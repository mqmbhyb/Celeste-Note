package com.bhyb.celestenote.ui.screen.note.noteoperation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhyb.celestenote.R
import com.bhyb.celestenote.ui.component.GridItem
import com.bhyb.celestenote.ui.component.GridSection

@Composable
fun NoteOperation(
    noteId: Int,
    closeBottomSheet: () -> Unit,
    viewModel: NoteOperationViewModel = hiltViewModel()
) {
    val isUpload by viewModel.isUpload.collectAsState(initial = false)

    LaunchedEffect(key1 = true) {
        viewModel.onGetNote(noteId)
    }

    Column {
        GridSection(
            imageSize = 25.dp,
            items = listOf(
                GridItem(R.drawable.ic_move, "移动") {
                    
                },
                GridItem(R.drawable.ic_lock1, "加密") {
                    closeBottomSheet()
                },
                GridItem(R.drawable.ic_upload, "上传", enabled = !isUpload) {
                    viewModel.onUpload(noteId)
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
            colors = ButtonColors(colorResource(R.color.bottom_navbar_color), Color.White, Color.Gray, Color.DarkGray),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        ) {
            Text("取消")
        }
    }
}
package com.bhyb.celestenote.ui.screen.note.noteoperation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.bhyb.celestenote.R
import com.bhyb.celestenote.ui.component.GridItem
import com.bhyb.celestenote.ui.component.GridSection

@Composable
fun NoteOperation(
    closeBottomSheet: () -> Unit
) {
    Column {
        GridSection(
            imageSize = 25.dp,
            items = listOf(
                GridItem(R.drawable.ic_move, "移动") {
                    closeBottomSheet()
                },
                GridItem(R.drawable.ic_lock1, "加密") {
                    closeBottomSheet()
                },
                GridItem(R.drawable.ic_upload, "上传") {
                    closeBottomSheet()
                },
                GridItem(R.drawable.ic_trash, "删除", MaterialTheme.colorScheme.error) {
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
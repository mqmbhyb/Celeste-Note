package com.bhyb.celestenote.ui.screen.note.drawer

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DeleteCategoryDialog(
    viewModel: DrawerViewModel,
    categoryId: Int,
    onDismissRequest: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.onGetNoteByCategory(categoryId)
    }

    val hasNotes = produceState(initialValue = false, producer = {
        viewModel.notesByCategory.collect { notes ->
            value = notes.isNotEmpty()
        }
    })

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color.White.copy(0.8f)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "确定要删除此分类吗?",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                if (hasNotes.value) {
                    Button(
                        onClick = {
                            viewModel.onDelete(DeleteCategoryEvent.DeleteCategoryAndNotes, categoryId)
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        border = BorderStroke(2.dp, Color.Red),
                        colors = ButtonColors(Color.White, Color.Red, Color.Gray, Color.DarkGray)
                    ) {
                        Text("删除分类和笔记")
                    }
                    Button(
                        onClick = {
                            viewModel.onDelete(DeleteCategoryEvent.DeleteCategory, categoryId)
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        colors = ButtonColors(Color.White, Color.DarkGray, Color.Gray, Color.DarkGray)
                    ) {
                        Text("仅删除分类")
                    }
                } else {
                    Button(
                        onClick = {
                            viewModel.onDelete(DeleteCategoryEvent.DeleteCategory, categoryId)
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        border = BorderStroke(2.dp, Color.Red),
                        colors = ButtonDefaults.buttonColors(Color.White, Color.Red)
                    ) {
                        Text("删除")
                    }
                }

                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.White, Color.DarkGray)
                ) {
                    Text("取消")
                }
            }
        }
    }
}
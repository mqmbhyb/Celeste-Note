package com.bhyb.celestenote.ui.screen.note.noteoperation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bhyb.celestenote.R
import com.bhyb.celestenote.ui.component.PassParametersToast

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CategorySelectionSheetContent(
    closeBottomSheet: () -> Unit,
    noteId: Int,
    onDismissRequest: () -> Unit,
    viewModel: NoteOperationViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val currentCategoryId by viewModel.currentCategoryId.collectAsState()
    val context = LocalContext.current

    val moveOk: (Int) -> Unit = { id ->
        viewModel.setNewCategoryId(id)
        viewModel.onMoveNote(noteId)
        closeBottomSheet()
        PassParametersToast.show(context, R.string.move_ok)
    }

    Column(
        modifier =  modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier =  modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "移动至分类",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier
                    .size(20.dp)
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
            ) {
                Icon(Icons.Default.Close, "Close")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            CategoryItem(currentCategoryId,"未分类笔记", R.drawable.ic_unsorted_notes, 0) {
                moveOk(0)
            }
            viewModel.categories.value.forEach { category ->
                CategoryItem(currentCategoryId,category.title, R.drawable.ic_document, category.id) {
                    moveOk(category.id)
                }

            }
        }
    }
}

@Composable
fun CategoryItem(
    currentCategoryId: Int,
    title: String,
    icon: Int,
    itemCategoryId: Int,
    updateNoteCategory: () -> Unit
) {
    val isSelected = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        if (currentCategoryId == itemCategoryId) {
            isSelected.value = true
        }
    }

    val color = if (isSelected.value) {
        LocalContentColor.current.copy(alpha = 0.38f)
    } else {
        LocalContentColor.current
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clickable {
                if (!isSelected.value) {
                    updateNoteCategory()
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Icon(painterResource(icon), null, modifier = Modifier.size(25.dp), tint = color)
        Spacer(modifier = Modifier.width(10.dp))
        Text(title, color = color)
        Spacer(modifier = Modifier.width(16.dp))
    }
}
package com.bhyb.celestenote.ui.screen.note.drawer

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhyb.celestenote.R

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DrawerContent(
    selectedItem: DrawerScreen,
    onItemSelected: (DrawerScreen) -> Unit,
    customDrawerItems: List<DrawerScreen>,
    drawerState: DrawerState,
    viewModel: DrawerViewModel = hiltViewModel()
) {
    val isNotEmpty = customDrawerItems.isNotEmpty()
    var showDialog by remember { mutableStateOf(false) }

    var showContextMenu by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<DrawerScreen?>(null) }
    var onUpdateClicked by remember { mutableStateOf(false) }
    var onRenameClicked by remember { mutableStateOf(false) }
    var showMoreVert by remember { mutableStateOf(false) }
    val category by viewModel.category.collectAsState(initial = null)
    var dialogText by remember { mutableStateOf(TextFieldValue()) }
    val onTextChange = { newText: TextFieldValue ->
        dialogText = newText
    }

    LaunchedEffect(drawerState.isClosed) {
        showMoreVert = false
        onUpdateClicked = false
    }

    LaunchedEffect(category) {
        dialogText = if (category != null) {
            TextFieldValue(category!!.title)
        } else {
            TextFieldValue()
        }
    }

    LaunchedEffect(dialogText) {
        if (dialogText.text.isNotBlank()) {
            if (onUpdateClicked) {
                val updatedText = dialogText.copy(selection = TextRange(dialogText.text.length))
                onTextChange(updatedText)
            } else {
                if (!showDialog) {
                    val updatedText = TextFieldValue()
                    onTextChange(updatedText)
                }
            }
        }
    }

    if (showDialog) {
        AddEditCategoryDialog(
            viewModel = viewModel,
            onDismissRequest = {
                viewModel.initCategory()
                viewModel.uiState.value.errorText = null
                showDialog = false
                selectedCategory = null
                onRenameClicked = false
            },
            onTextChange = onTextChange,
            dialogText = dialogText,
            isEditMode = onUpdateClicked,
            categoryToEdit = category
        )
    }

    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        modifier = Modifier
            .width(300.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(id = R.string.note_category),
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, bottom = 10.dp),
                fontWeight = FontWeight.Black
            )
        }

        LazyColumn {
            items(drawerItems) { item ->
                NavigationDrawerItem(
                    label = { Text(item.title) },
                    icon = {
                        Icon(
                            painterResource(item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    selected = item == selectedItem,
                    onClick = {
                        onItemSelected(item)
                        showMoreVert = !showMoreVert
                        onUpdateClicked = !onUpdateClicked
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(id = R.string.note_my_category), fontWeight = FontWeight.Black)
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!onUpdateClicked) {
                    Text(
                        text = "修改",
                        modifier = Modifier.clickable(enabled = isNotEmpty) {
                            showMoreVert = !showMoreVert
                            onUpdateClicked = !onUpdateClicked
                        },
                        color = if (isNotEmpty) {
                            colorResource(R.color.bottom_navbar_color)
                        } else {
                            colorResource(R.color.bottom_navbar_color).copy(alpha = 0.5f)
                        }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "新建",
                        modifier = Modifier.clickable {
                            showDialog = true
                        },
                        color = colorResource(R.color.bottom_navbar_color)
                    )
                } else {
                    Text(
                        text = "完成",
                        modifier = Modifier.clickable {
                            viewModel.initCategory()
                            showMoreVert = !showMoreVert
                            onUpdateClicked = !onUpdateClicked
                        },
                        color = colorResource(R.color.bottom_navbar_color)
                    )
                }

            }
        }

        if (isNotEmpty) {
            LazyColumn {
                items(customDrawerItems) { item ->
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .animateContentSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NavigationDrawerItem(
                            label = { Text(item.title, overflow = TextOverflow.Ellipsis) },
                            icon = {
                                Icon(
                                    painterResource(item.icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            selected = item == selectedItem,
                            onClick = {
                                onItemSelected(item)
                                showMoreVert = !showMoreVert
                                onUpdateClicked = !onUpdateClicked
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = if (showMoreVert) 10.dp else 0.dp)
                        )
                        if (showMoreVert) {
                            IconButton(
                                onClick = {
                                    selectedCategory = item
                                    showContextMenu = true
                                }
                            ) {
                                Icon(Icons.Default.MoreVert, null)
                            }
                            DropdownMenu(
                                expanded = showContextMenu && selectedCategory == item,
                                onDismissRequest = { showContextMenu = false },
                                modifier = Modifier.background(Color.White),
                                offset = DpOffset(200.dp, 0.dp)
                            ) {
                                selectedCategory?.let { category ->
                                    DropdownMenuItem(
                                        text = { Text("重命名") },
                                        onClick = {
                                            selectedCategory?.id?.let { viewModel.onGetCategory(it) }
                                            showContextMenu = false
                                            onRenameClicked = true
                                            showDialog = true
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("删除", color = MaterialTheme.colorScheme.error) },
                                        onClick = {
                                            category.id?.let { viewModel.onDeleteCategory(it) }
                                            showContextMenu = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
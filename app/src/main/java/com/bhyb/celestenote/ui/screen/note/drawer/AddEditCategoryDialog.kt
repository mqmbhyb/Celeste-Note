package com.bhyb.celestenote.ui.screen.note.drawer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bhyb.celestenote.R
import com.bhyb.celestenote.domain.model.Category

@Composable
fun AddEditCategoryDialog(
    viewModel: DrawerViewModel,
    onDismissRequest: () -> Unit,
    onTextChange: (TextFieldValue) -> Unit,
    dialogText: TextFieldValue,
    isEditMode: Boolean,
    categoryToEdit: Category? = null
) {
    val color: Color = colorResource(R.color.bottom_navbar_color)
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }

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
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

                TextField(
                    value = dialogText,
                    onValueChange = { newText ->
                        onTextChange(newText)
                        viewModel.categoryTitle.value = newText.text
                        viewModel.uiState.value.errorText = null
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.checkCategoryTitleAndSave(
                            title = dialogText.text,
                            isEditMode = isEditMode,
                            categoryToEdit = categoryToEdit,
                            onDismissRequest = onDismissRequest
                        )
                        viewModel.initCategory()
                    },
                    enabled = !uiState.isCheckingDuplicate,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    border = BorderStroke(2.dp, color),
                    colors = ButtonDefaults.buttonColors(Color.White, color)
                ) {
                    Text("保存")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.White, Color.DarkGray)
                ) {
                    Text("取消")
                }

                uiState.errorText?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
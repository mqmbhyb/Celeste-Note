package com.bhyb.celestenote.ui.screen.my.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bhyb.celestenote.R

@Composable
fun AboutSheetContent(
    modifier: Modifier = Modifier
) {
    val openCopyrightSheet = remember { mutableStateOf(false) }
    val openPrivacyPolicySheet = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "关于",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
                .padding(bottom = 16.dp)
        )

        Spacer(modifier = modifier.height(10.dp))

        Image(painter = rememberAsyncImagePainter(model = R.mipmap.ic_launcher), contentDescription = null)
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = "Celeste Note",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = modifier.height(20.dp))

        Row {
            Button(
                onClick = { openCopyrightSheet.value = true },
                modifier = modifier.weight(1f)
            ) {
                Text("版权声明")
            }
            Spacer(modifier = modifier.width(6.dp))
            Button(
                onClick = { openPrivacyPolicySheet.value = true },
                modifier = modifier.weight(1f)
            ) {
                Text("隐私政策")
            }
        }

        Spacer(modifier = modifier.height(12.dp))

        if (openCopyrightSheet.value) {
            ShowBottomSheet(
                sheetContent = {
                    GeneralSheetContent(
                        title = "版权声明",
                        content = "这里是版权声明的详细内容...",
                        onDismissRequest = {
                            openCopyrightSheet.value = false
                        }
                    )
                },
                onDismissRequest = {
                    openCopyrightSheet.value = false
                }
            )
        }

        if (openPrivacyPolicySheet.value) {
            ShowBottomSheet(
                sheetContent = {
                    GeneralSheetContent(
                        title = "隐私政策",
                        content = "这里是隐私政策的详细内容...",
                        onDismissRequest = {
                            openPrivacyPolicySheet.value = false
                        }
                    )
                },
                onDismissRequest = {
                    openPrivacyPolicySheet.value = false
                }
            )
        }
    }
}

@Composable
fun GeneralSheetContent(
    title: String,
    content: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(
                onClick = { onDismissRequest() },
                colors = IconButtonColors(colorResource(id = R.color.screen_background_color), Color.Gray.copy(0.5f), Color.Gray, Color.Gray),
                modifier = modifier.size(20.dp)
            ) {
                Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "收起")
            }
        }

        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .padding(bottom = 16.dp)
        )
    }
}
package com.bhyb.celestenote.ui.screen.my.bottomsheet

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBottomSheet(
    sheetContent: @Composable () -> Unit,
    onDismissRequest: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    // 当ModalBottomSheet关闭时重置sheetContent
    DisposableEffect(modalBottomSheetState) {
        onDispose {
            if (!modalBottomSheetState.isVisible) {
                onDismissRequest()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = modalBottomSheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = Color.White,
        content = {
            sheetContent.invoke()
        }
    )
}
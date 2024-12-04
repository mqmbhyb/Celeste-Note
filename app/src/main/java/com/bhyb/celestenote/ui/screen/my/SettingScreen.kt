package com.bhyb.celestenote.ui.screen.my

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
) {
    Text("Setting Screen", Modifier.fillMaxSize(), textAlign = TextAlign.Center)
}
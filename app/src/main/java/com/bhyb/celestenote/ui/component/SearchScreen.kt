package com.bhyb.celestenote.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
   Text("Search Screen", Modifier.fillMaxSize(), textAlign = TextAlign.Center)
}
package com.bhyb.celestenote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bhyb.celestenote.ui.theme.CelesteNoteTheme
import androidx.navigation.compose.rememberNavController
import com.bhyb.celestenote.compose.bottomnavbar.BottomNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CelesteNoteTheme {
                BottomNav()
            }
        }
    }
}
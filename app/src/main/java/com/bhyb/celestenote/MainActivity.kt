package com.bhyb.celestenote

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.bhyb.celestenote.route.AppNavHost
import com.bhyb.celestenote.ui.screen.my.LocalUserManager
import com.bhyb.celestenote.ui.screen.my.UserManager
import com.bhyb.celestenote.ui.theme.CelesteNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val userManager = remember { UserManager() }
            val navController = rememberNavController()

            CompositionLocalProvider(LocalUserManager provides userManager) {
                CelesteNoteTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        AppNavHost(navController = navController)
                    }
                }
            }
        }
    }
}


package com.example.deliciousfood.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import com.example.deliciousfood.ui.NavGraph
import com.example.deliciousfood.ui.utils.LocalBackDispatcher
import com.example.deliciousfood.ui.theme.DeliciousFoodTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ComposeApp {
                CompositionLocalProvider(LocalBackDispatcher.provides(onBackPressedDispatcher)) {
                        NavGraph()
                }
            }
        }
    }
}
@Composable
fun ComposeApp(content: @Composable () -> Unit) {
    DeliciousFoodTheme {
        content()
    }
}








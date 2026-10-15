package dev.mindfuldays.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.mindfuldays.app.ui.home.HomeScreen
import dev.mindfuldays.app.ui.settings.SettingsScreen
import dev.mindfuldays.app.ui.theme.MindfulDaysTheme
import dev.mindfuldays.app.ui.timer.TimerScreen
import dev.mindfuldays.app.ui.viewmodel.MindfulnessViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindfulDaysTheme {
                androidx.compose.material3.Surface(
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    MindfulDaysApp()
                }
            }
        }
    }
}

@Composable
fun MindfulDaysApp(
    viewModel: MindfulnessViewModel = viewModel()
) {
    var currentScreen by remember { mutableStateOf("home") }

    when (currentScreen) {
        "home" -> HomeScreen(
            viewModel = viewModel,
            onNavigateToSettings = { currentScreen = "settings" },
            onNavigateToTimer = { currentScreen = "timer" }
        )
        "timer" -> TimerScreen(
            onNavigateBack = { currentScreen = "home" }
        )
        "settings" -> SettingsScreen(
            onNavigateBack = { currentScreen = "home" }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MindfulDaysAppPreview() {
    MindfulDaysTheme {
        MindfulDaysApp()
    }
}

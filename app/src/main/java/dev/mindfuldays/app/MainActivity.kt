package dev.mindfuldays.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.mindfuldays.app.ui.home.HomeScreen
import dev.mindfuldays.app.ui.settings.SettingsScreen
import dev.mindfuldays.app.ui.theme.MindfulDaysTheme
import dev.mindfuldays.app.ui.timer.TimerScreen
import dev.mindfuldays.app.ui.viewmodel.MindfulnessViewModel

enum class AppScreen {
    HOME,
    TIMER,
    SETTINGS
}

class MainActivity : ComponentActivity() {

    private val viewModel: MindfulnessViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MindfulDaysTheme {
                MindfulDaysApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MindfulDaysApp(viewModel: MindfulnessViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "ScreenTransition"
    ) { screen ->
        when (screen) {
            AppScreen.HOME -> {
                HomeScreen(
                    attitude = uiState.attitude,
                    aiReflection = uiState.aiReflection,
                    isLoadingAi = uiState.isLoadingAi,
                    onGenerateAiReflection = { viewModel.generateAiReflection() },
                    onNavigateToTimer = { currentScreen = AppScreen.TIMER },
                    onNavigateToSettings = { currentScreen = AppScreen.SETTINGS }
                )
            }
            AppScreen.TIMER -> {
                TimerScreen(
                    secondsRemaining = uiState.timerSecondsRemaining,
                    isRunning = uiState.isTimerRunning,
                    isFinished = uiState.isTimerFinished,
                    onStart = { viewModel.startTimer() },
                    onPause = { viewModel.pauseTimer() },
                    onReset = { viewModel.resetTimer() },
                    onNavigateBack = { currentScreen = AppScreen.HOME }
                )
            }
            AppScreen.SETTINGS -> {
                SettingsScreen(
                    dailyReminderEnabled = uiState.dailyReminderEnabled,
                    meditationReminderEnabled = uiState.meditationReminderEnabled,
                    onToggleDailyReminder = { viewModel.toggleDailyReminder(it) },
                    onToggleMeditationReminder = { viewModel.toggleMeditationReminder(it) },
                    onNavigateBack = { currentScreen = AppScreen.HOME }
                )
            }
        }
    }
}

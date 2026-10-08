package dev.mindfuldays.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mindfuldays.app.data.model.MindfulnessAttitude
import dev.mindfuldays.app.data.remote.GeminiApiService
import dev.mindfuldays.app.data.repository.AttitudeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MindfulnessUiState(
    val attitude: MindfulnessAttitude,
    val aiReflection: String = "",
    val isLoadingAi: Boolean = false,
    val timerSecondsRemaining: Int = 10 * 60, // 10 minutes
    val isTimerRunning: Boolean = false,
    val isTimerFinished: Boolean = false,
    val dailyReminderEnabled: Boolean = true,
    val meditationReminderEnabled: Boolean = false
)

class MindfulnessViewModel(
    private val repository: AttitudeRepository = AttitudeRepository(),
    private val geminiService: GeminiApiService = GeminiApiService()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MindfulnessUiState(attitude = repository.getTodayAttitude())
    )
    val uiState: StateFlow<MindfulnessUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun generateAiReflection() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingAi = true) }
            val attitudeTitle = _uiState.value.attitude.title
            val reflection = geminiService.generateReflection(attitudeTitle)
            _uiState.update {
                it.copy(
                    aiReflection = reflection,
                    isLoadingAi = false
                )
            }
        }
    }

    fun startTimer() {
        if (_uiState.value.isTimerRunning) return
        if (_uiState.value.timerSecondsRemaining <= 0) {
            _uiState.update { it.copy(timerSecondsRemaining = 10 * 60, isTimerFinished = false) }
        }

        _uiState.update { it.copy(isTimerRunning = true, isTimerFinished = false) }

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.timerSecondsRemaining > 0 && _uiState.value.isTimerRunning) {
                delay(1000L)
                _uiState.update {
                    val next = it.timerSecondsRemaining - 1
                    it.copy(
                        timerSecondsRemaining = next,
                        isTimerFinished = next == 0,
                        isTimerRunning = next > 0
                    )
                }
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _uiState.update { it.copy(isTimerRunning = false) }
    }

    fun resetTimer(totalSeconds: Int = 10 * 60) {
        timerJob?.cancel()
        _uiState.update {
            it.copy(
                timerSecondsRemaining = totalSeconds,
                isTimerRunning = false,
                isTimerFinished = false
            )
        }
    }

    fun toggleDailyReminder(enabled: Boolean) {
        _uiState.update { it.copy(dailyReminderEnabled = enabled) }
    }

    fun toggleMeditationReminder(enabled: Boolean) {
        _uiState.update { it.copy(meditationReminderEnabled = enabled) }
    }
}

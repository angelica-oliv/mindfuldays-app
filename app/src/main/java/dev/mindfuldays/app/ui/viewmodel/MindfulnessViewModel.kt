package dev.mindfuldays.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mindfuldays.app.data.model.MindfulnessAttitude
import dev.mindfuldays.app.data.remote.GeminiApiService
import dev.mindfuldays.app.data.repository.AttitudeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

// TODO: Connect Gemini API integration in Module 3
data class MindfulnessUiState(
    val attitude: MindfulnessAttitude,
    val aiReflection: String = "“Acolher o presente não é conformismo, é o ponto de partida para qualquer transformação real.”",
    val isLoadingAi: Boolean = false
)

class MindfulnessViewModel(
    private val repository: AttitudeRepository = AttitudeRepository(),
    private val geminiService: GeminiApiService = GeminiApiService()
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MindfulnessUiState(attitude = repository.getTodayAttitude())
    )
    val uiState: StateFlow<MindfulnessUiState> = _uiState.asStateFlow()

    fun generateAiReflection(
        attitudeTitle: String = _uiState.value.attitude.name,
        languageCode: String = Locale.getDefault().language
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingAi = true) }
            delay(1000) // Simulated delay for UI preview
            val reflection = geminiService.generateReflection(attitudeTitle, languageCode)
            _uiState.update {
                it.copy(
                    aiReflection = reflection,
                    isLoadingAi = false
                )
            }
        }
    }
}
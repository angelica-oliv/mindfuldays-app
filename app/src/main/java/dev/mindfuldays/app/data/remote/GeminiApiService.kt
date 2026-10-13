package dev.mindfuldays.app.data.remote

// TODO: Implement Google Gemini API call with Retrofit in Module 3
class GeminiApiService {
    suspend fun generateReflection(attitudeTitle: String, languageCode: String = "pt"): String =
        "Respire fundo e traga sua atenção para o momento presente."
}

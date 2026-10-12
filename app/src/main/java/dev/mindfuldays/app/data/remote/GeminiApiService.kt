package dev.mindfuldays.app.data.remote

import dev.mindfuldays.app.BuildConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Locale

@JsonClass(generateAdapter = true)
data class GeminiRequest(val contents: List<Content>)

@JsonClass(generateAdapter = true)
data class Content(val parts: List<Part>)

@JsonClass(generateAdapter = true)
data class Part(val text: String)

@JsonClass(generateAdapter = true)
data class GeminiResponse(val candidates: List<Candidate>?)

@JsonClass(generateAdapter = true)
data class Candidate(val content: ContentResponse?)

@JsonClass(generateAdapter = true)
data class ContentResponse(val parts: List<PartResponse>?)

@JsonClass(generateAdapter = true)
data class PartResponse(val text: String?)

interface GeminiApi {
    @POST("v1beta/models/gemini-3.8-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

class GeminiApiService {

    private val api: GeminiApi by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeminiApi::class.java)
    }

    /**
     * Gera uma reflexão diária personalizada para a atitude informada usando o modelo gemini-3.8-flash,
     * adaptando o idioma do prompt e resposta para o idioma do usuário (pt, en, es).
     */
    suspend fun generateReflection(
        attitudeTitle: String,
        languageCode: String = Locale.getDefault().language
    ): String = withContext(Dispatchers.IO) {
        try {
            val apiKey = BuildConfig.GEMINI_API_KEY
            if (apiKey.isBlank()) {
                return@withContext getOfflineFallback(languageCode)
            }

            val (systemInstruction, userPrompt) = when (languageCode.lowercase().take(2)) {
                "en" -> Pair(
                    "You are a calm, wise and welcoming mindfulness assistant in the MindfulDays application. " +
                        "Your purpose is to generate serene reflections to cultivate daily mindfulness based on Jon Kabat-Zinn's 9 attitudes. " +
                        "Always respond with at most 2 short, poetic and inspiring sentences. " +
                        "CRITICAL REQUIREMENT: Your entire response MUST be written in English. " +
                        "Never use complex formatting or markdown, use simple and serene plain text.",
                    "Write an inspiring 2-sentence reflection about the mindfulness attitude: \"$attitudeTitle\".\n" +
                        "Requested Language: English. Please write the entire reflection strictly in English."
                )
                "es" -> Pair(
                    "Eres un asistente sereno, sabio y acogedor de mindfulness en la aplicación MindfulDays. " +
                        "Tu propósito es generar reflexiones breves para cultivar la atención plena diaria según las 9 actitudes de Jon Kabat-Zinn. " +
                        "Responde siempre con un máximo de 2 oraciones breves, poéticas e inspiradoras. " +
                        "REQUISITO OBLIGATORIO: Toda tu respuesta DEBE estar escrita en español. " +
                        "Nunca utilices markdown complejo ni caracteres especiales, utiliza texto plano y sereno.",
                    "Escribe una reflexión inspiradora de 2 oraciones sobre la actitud de mindfulness: \"$attitudeTitle\".\n" +
                        "Idioma solicitado: Español. Por favor, escribe toda la reflexión estrictamente en español."
                )
                else -> Pair(
                    "Você é um assistente calmo, sábio e acolhedor de mindfulness no aplicativo MindfulDays. " +
                        "Seu propósito é gerar reflexões serenas para cultivar a atenção plena no dia a dia com base nas 9 atitudes de Jon Kabat-Zinn. " +
                        "Responda sempre com no máximo 2 frases curtas, poéticas e inspiradoras. " +
                        "REQUISITO OBRIGATÓRIO: Toda a sua resposta DEVE ser gerada em português. " +
                        "Nunca use formatação complexa ou markdown, utilize texto simples e sereno.",
                    "Escreva uma reflexão inspiradora de 2 frases sobre a atitude de mindfulness: \"$attitudeTitle\".\n" +
                        "Idioma solicitado: Português. Por favor, escreva toda a reflexão estritamente em português."
                )
            }

            val request = GeminiRequest(
                contents = listOf(
                    Content(parts = listOf(Part(text = "$systemInstruction\n\n$userPrompt")))
                )
            )

            val response = api.generateContent(apiKey = apiKey, request = request)
            val reflection = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text?.trim()
            reflection ?: getDefaultFallback(languageCode)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            android.util.Log.e("GeminiApiService", "Error generating reflection via Retrofit", e)
            getOfflineFallback(languageCode)
        }
    }

    private fun getOfflineFallback(languageCode: String): String = when (languageCode.lowercase()) {
        "en" -> "Connected in offline mode. Practice this reflection: Take a deep breath and bring your awareness to the present moment."
        "es" -> "Conectado en modo offline. Practica la reflexión: Respira profundo y enfoca tu atención en el momento presente."
        else -> "Conectado no modo offline. Pratique a reflexão: Respire fundo e traga sua atenção para o momento presente."
    }

    private fun getDefaultFallback(languageCode: String): String = when (languageCode.lowercase()) {
        "en" -> "Welcome the present moment exactly as it unfolds."
        "es" -> "Acoge el momento presente exactamente como se manifiesta."
        else -> "Acolha o momento presente exatamente como ele se manifesta."
    }
}

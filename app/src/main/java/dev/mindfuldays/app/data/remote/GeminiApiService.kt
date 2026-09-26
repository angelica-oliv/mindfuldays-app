package dev.mindfuldays.app.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class GeminiApiService(var apiKey: String = "") {

    suspend fun generateReflection(attitudeTitle: String): String = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) {
            return@withContext "Insira sua API Key do Gemini nas configurações para gerar reflexões personalizadas."
        }

        try {
            val url = URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.doOutput = true

            val prompt = "Escreva uma reflexão inspiradora de 2 frases sobre a atitude de mindfulness: $attitudeTitle."
            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().put(JSONObject().apply {
                    put("parts", JSONArray().put(JSONObject().apply {
                        put("text", prompt)
                    }))
                }))
            }

            connection.outputStream.use { os ->
                os.write(jsonBody.toString().toByteArray())
            }

            if (connection.responseCode == 200) {
                val responseText = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonResponse = JSONObject(responseText)
                val text = jsonResponse
                    .getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text")
                text.trim()
            } else {
                "Não foi possível conectar ao Gemini (${connection.responseCode}). Tente novamente em instantes."
            }
        } catch (e: Exception) {
            "Conectado no modo offline. Pratique a reflexão: Respire fundo e traga sua atenção para o momento presente."
        }
    }
}

package dev.mindfuldays.app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dev.mindfuldays.app.data.model.MindfulnessAttitude
import dev.mindfuldays.app.ui.home.HomeScreen
import dev.mindfuldays.app.ui.theme.MindfulDaysTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun exibirTituloDaAtitudeEBotaoDeIaNaHomeScreen() {
        val attitude = MindfulnessAttitude(3, "Aceitação", "Acolha o momento presente.")
        var clickedAi = false

        composeTestRule.setContent {
            MindfulDaysTheme {
                HomeScreen(
                    attitude = attitude,
                    aiReflection = "Reflexão de teste",
                    isLoadingAi = false,
                    onGenerateAiReflection = { clickedAi = true },
                    onNavigateToTimer = {}
                )
            }
        }

        // Verifica renderização dos nós
        composeTestRule.onNodeWithText("Aceitação").assertExists()
        composeTestRule.onNodeWithText("Reflexão de teste").assertExists()

        // Dispara clique no botão da IA
        composeTestRule.onNodeWithText("✨ Nova Reflexão (Gemini IA)").performClick()
        assert(clickedAi)
    }
}

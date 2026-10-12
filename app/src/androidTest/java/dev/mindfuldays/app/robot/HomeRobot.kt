package dev.mindfuldays.app.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

/**
 * Robot Pattern para a HomeScreen.
 * Encapsula as interações e asserções da interface do usuário em Jetpack Compose,
 * tornando os testes declarativos, legíveis e fáceis de manter.
 */
class HomeRobot(private val composeTestRule: ComposeContentTestRule) {

    fun assertTitleDisplayed(title: String) = apply {
        composeTestRule.onNodeWithText(title).assertIsDisplayed()
    }

    fun assertAttitudeName(name: String) = apply {
        composeTestRule.onNodeWithText(name).assertIsDisplayed()
    }

    fun assertReflectionText(reflection: String) = apply {
        composeTestRule.onNodeWithText(reflection).assertIsDisplayed()
    }

    fun clickGenerateReflection(buttonText: String) = apply {
        composeTestRule.onNodeWithText(buttonText).performClick()
    }

    fun clickSettingsButton(contentDescription: String) = apply {
        composeTestRule.onNodeWithContentDescription(contentDescription).performClick()
    }

    fun clickTimerButton(buttonText: String) = apply {
        composeTestRule.onNodeWithText(buttonText).performClick()
    }
}

/**
 * Função DSL para inicializar e executar passos com o HomeRobot.
 */
fun homeRobot(
    composeTestRule: ComposeContentTestRule,
    block: HomeRobot.() -> Unit
): HomeRobot = HomeRobot(composeTestRule).apply(block)

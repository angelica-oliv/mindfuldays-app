package dev.mindfuldays.app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.mindfuldays.app.data.model.MindfulnessAttitude
import dev.mindfuldays.app.robot.homeRobot
import dev.mindfuldays.app.ui.home.HomeScreenContent
import dev.mindfuldays.app.ui.theme.MindfulDaysTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenRobotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_exibeAttitudeEReflexao_eDisparaAcoes() {
        var clickedAi = false
        var clickedTimer = false
        val attitude = MindfulnessAttitude(
            id = 3,
            name = "Aceitação",
            description = "Reconheça e acolha o momento presente exatamente como ele é.",
            nameRes = R.string.attitude_3_name,
            descriptionRes = R.string.attitude_3_desc
        )

        composeTestRule.setContent {
            MindfulDaysTheme {
                HomeScreenContent(
                    attitude = attitude,
                    aiReflection = "Reflexão de teste para o workshop",
                    isLoadingAi = false,
                    onGenerateAiReflection = { clickedAi = true },
                    onNavigateToSettings = {},
                    onNavigateToTimer = { clickedTimer = true }
                )
            }
        }

        val context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

        // Execução expressiva com o Robot Pattern
        homeRobot(composeTestRule) {
            assertReflectionText("Reflexão de teste para o workshop")
            clickGenerateReflection(context.getString(R.string.btn_new_reflection))
            clickTimerButton(context.getString(R.string.btn_start_timer))
        }

        assertTrue(clickedAi)
        assertTrue(clickedTimer)
    }
}

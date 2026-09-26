package dev.mindfuldays.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = SageGreen,
    secondary = SandMuted,
    background = WarmOffWhite,
    surface = PureWhite,
    onPrimary = PureWhite,
    onBackground = ForestDark,
    onSurface = ForestDark
)

@Composable
fun MindfulDaysTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}

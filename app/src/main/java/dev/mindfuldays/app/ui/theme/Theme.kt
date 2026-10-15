package dev.mindfuldays.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = SageGreen,
    onPrimary = PureWhite,
    primaryContainer = SageGreenLight,
    onPrimaryContainer = PureWhite,
    secondary = MutedSand,
    onSecondary = DarkForestGreen,
    background = WarmOffWhite,
    onBackground = DarkForestGreen,
    surface = PureWhite,
    onSurface = DarkForestGreen,
    surfaceVariant = Color(0xFFF2ECE6),
    onSurfaceVariant = MutedForestGreen,
    outline = MutedSand,
    outlineVariant = MutedSand
)

private val DarkColorScheme = darkColorScheme(
    primary = LuminousSage,
    onPrimary = Color(0xFF1E3500),
    primaryContainer = ForestSurfaceVariant,
    onPrimaryContainer = LuminousSage,
    secondary = SoftMutedSage,
    onSecondary = ForestNight,
    background = ForestNight,
    onBackground = WarmOffWhiteText,
    surface = ForestSurface,
    onSurface = WarmOffWhiteText,
    surfaceVariant = ForestSurfaceVariant,
    onSurfaceVariant = SoftMutedSage,
    outline = ForestOutline,
    outlineVariant = ForestOutline
)

@Composable
fun MindfulDaysTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

package dev.mindfuldays.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mindfuldays.app.R
import dev.mindfuldays.app.data.model.MindfulnessAttitude
import dev.mindfuldays.app.data.repository.AttitudeRepository
import dev.mindfuldays.app.ui.theme.MindfulDaysTheme
import dev.mindfuldays.app.ui.viewmodel.MindfulnessViewModel

@Composable
fun HomeScreen(
    viewModel: MindfulnessViewModel,
    onNavigateToSettings: () -> Unit,
    onNavigateToTimer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val localizedAttitudeName = if (uiState.attitude.nameRes != 0) {
        stringResource(uiState.attitude.nameRes)
    } else {
        uiState.attitude.name
    }

    HomeScreenContent(
        attitude = uiState.attitude,
        aiReflection = uiState.aiReflection,
        isLoadingAi = uiState.isLoadingAi,
        onGenerateAiReflection = {
            viewModel.generateAiReflection(attitudeTitle = localizedAttitudeName)
        },
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToTimer = onNavigateToTimer,
        modifier = modifier
    )
}

@Composable
fun HomeScreenContent(
    attitude: MindfulnessAttitude,
    aiReflection: String,
    isLoadingAi: Boolean,
    onGenerateAiReflection: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToTimer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFBF9F5))
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header (Top Bar)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(48.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.app_title),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFF6B8E23),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.attitude_counter, attitude.id, 9),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onNavigateToSettings) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.cd_settings)
                )
            }
        }

        // Card Central (ElevatedCard)
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color(0xFFFFFFFF)
            ),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (attitude.nameRes != 0) stringResource(attitude.nameRes) else attitude.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (attitude.descriptionRes != 0) stringResource(attitude.descriptionRes) else attitude.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                HorizontalDivider(
                    color = Color(0xFFE8DFD8),
                    thickness = 1.dp
                )
                Text(
                    text = if (aiReflection.isNotBlank()) aiReflection else stringResource(R.string.default_reflection),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = onGenerateAiReflection,
                    enabled = !isLoadingAi,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoadingAi) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(R.string.btn_new_reflection))
                    }
                }
            }
        }

        // Ação de Navegação para o Timer
        OutlinedButton(
            onClick = onNavigateToTimer,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.btn_start_timer),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MindfulDaysTheme {
        HomeScreenContent(
            attitude = AttitudeRepository().attitudes.first(),
            aiReflection = "Cultivar o olhar curioso abre portas para descobertas simples e profundas a cada instante.",
            isLoadingAi = false,
            onGenerateAiReflection = {},
            onNavigateToSettings = {},
            onNavigateToTimer = {}
        )
    }
}
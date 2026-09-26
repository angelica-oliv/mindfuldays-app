package dev.mindfuldays.app.ui.timer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mindfuldays.app.ui.theme.SageGreen
import dev.mindfuldays.app.ui.theme.SandMuted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    secondsRemaining: Int,
    isRunning: Boolean,
    isFinished: Boolean,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val totalSeconds = 10 * 60
    val progress = if (totalSeconds > 0) secondsRemaining.toFloat() / totalSeconds.toFloat() else 0f
    val minutes = secondsRemaining / 60
    val seconds = secondsRemaining % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Timer de Meditação") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = if (isFinished) "Sessão concluída! Respire em paz. 🕊️" else "Concentre-se na respiração",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            // Timer Circular
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(260.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Círculo de fundo
                    drawCircle(
                        color = SandMuted,
                        style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                    )
                    // Arco de progresso
                    drawArc(
                        color = SageGreen,
                        startAngle = -90f,
                        sweepAngle = 360f * progress,
                        useCenter = false,
                        style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = formattedTime,
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 44.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isRunning) "Em andamento" else if (isFinished) "Finalizado" else "Pausado",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }

            // Controles: Play, Pause e Reset
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reset Button
                OutlinedIconButton(
                    onClick = onReset,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reiniciar",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))

                // Play / Pause Button
                FilledIconButton(
                    onClick = { if (isRunning) onPause() else onStart() },
                    modifier = Modifier.size(68.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    if (isRunning) {
                        Text("⏸", fontSize = 28.sp)
                    } else {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Iniciar",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }
    }
}

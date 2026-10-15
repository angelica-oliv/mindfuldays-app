# 🧘 MindfulDays — Diretrizes e Contexto para Agentes de IA

Este arquivo atua como a fonte de verdade para agentes de IA e ferramentas de assistência de código que atuam neste repositório.

## 📱 Visão Geral do Projeto
O **MindfulDays** é um aplicativo Android nativo focado em mindfulness e saúde mental, baseado nas **9 Atitudes de Mindfulness de Jon Kabat-Zinn**, com suporte a reflexões diárias geradas via **Google Gemini API** e um timer circular de meditação.

## 🏗️ Padrões de Arquitetura e Stack Técnica
- **Linguagem**: Kotlin 1.9+ (uso idiomático de coroutines, StateFlow e imutabilidade).
- **UI Toolkit**: Jetpack Compose com Material Design 3 (M3). **Proibido o uso de layouts XML tradicionais**.
- **Padrão de Arquitetura**: MVVM (Model-View-ViewModel) alinhado com Clean Architecture:
    - `data/model/`: Data classes imutáveis com `@StringRes` para i18n.
    - `data/repository/`: Repositórios para fonte de dados local e lógica das 9 atitudes.
    - `data/remote/`: Clientes e serviços para comunicação com a Gemini API via Retrofit e Moshi.
    - `ui/`: Telas e componentes em Compose divididos por feature (`home`, `timer`, `settings`).
    - `ui/theme/`: Tokens visuais (Color, Theme, Type).
    - `ui/viewmodel/`: ViewModels expondo estados via `StateFlow`.
- **Pacote Base**: `dev.mindfuldays.app`.

## 🎨 Design Tokens & Temas (Material Design 3 — Light & Dark Mode)
Respeite estritamente os tokens de cores semânticos do Material 3, garantindo suporte perfeito e contraste adequado tanto em tema claro quanto escuro:

### Tema Claro (Light Theme):
- `primary`: Verde Sálvia (`#6B8E23`)
- `primaryContainer`: Sálvia Suave (`#8FA853`)
- `secondary`: Areia Muted (`#E8DFD8`)
- `background`: Off-White Calmo (`#FBF9F5`)
- `surface`: Branco Puro (`#FFFFFF`)
- `onBackground` / `onSurface`: Verde Floresta Escuro (`#2C3E35`)
- `onSurfaceVariant`: Verde Floresta Suave (`#5C6F64`)
- `outlineVariant`: Areia Muted (`#E8DFD8`)

### Tema Escuro (Dark Theme):
- `primary`: Sálvia Luminoso (`#A8C673`)
- `primaryContainer`: Floresta Escuro Elevado (`#2E4239`)
- `secondary`: Sálvia Suave (`#A3B5A8`)
- `background`: Floresta Noturno Profundo (`#15201A`)
- `surface`: Floresta Noturno Elevado (`#22322A`)
- `onBackground` / `onSurface`: Off-White Claro (`#F1EFEA`)
- `onSurfaceVariant`: Sálvia Muted Claro (`#A3B5A8`)
- `outlineVariant`: Borda Sutil Floresta (`#3B5045`)

## 🧠 Regras de Negócio Fundamentais
1. **Regra das 9 Atitudes**: A lista contém exatamente 9 atitudes fixas. O cálculo da atitude diária deve usar o dia do ano:
   `val index = (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1).mod(9)`
2. **Gemini API (Reflexões Diárias)**:
    - Utilizar o endpoint do modelo `gemini-3.8-flash:generateContent`.
    - Gerar reflexões concisas e inspiradoras com no máximo 2 frases para cada atitude, respeitando o idioma do usuário (pt, en, es).
    - Tratar ausência de chave de API e erros de rede com fallback amigável offline sem quebrar o app.

## 🛠️ Comandos de Verificação e Validação
Sempre verifique as alterações utilizando os seguintes comandos no terminal:
- **Compilação do app**: `./gradlew assembleDebug`
- **Testes Unitários**: `./gradlew testDebugUnitTest`
- **Testes de UI (Robot Pattern)**: `./gradlew connectedDebugAndroidTest`

## 🚫 Restrições Rígidas (Guardrails)
- **NUNCA faça hardcode de cores hexadecimais em Composables** (`Color(0xFF...)`). Utilize sempre `MaterialTheme.colorScheme.background`, `MaterialTheme.colorScheme.surface`, `MaterialTheme.colorScheme.onSurface`, etc., para evitar problemas de contraste no Dark Mode.
- **NUNCA** faça hardcode de credenciais ou chaves de API (`apiKey`) no código versionado.
- **NÃO** misture lógica de negócios ou chamadas assíncronas de rede diretamente dentro de funções `@Composable`.
- Mantenha funções Composable limpas e desacopladas, utilizando parâmetros para eventos e lambdas (`onAction: () -> Unit`) para facilitar testes de UI.
- Use nomes descritivos em português para textos de interface visível (carregados via `stringResource(R.string...)`) e inglês para código técnico, métodos e classes.
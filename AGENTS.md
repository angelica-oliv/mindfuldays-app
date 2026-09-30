# 🧘 MindfulDays — Diretrizes e Contexto para Agentes de IA

Este arquivo atua como a fonte de verdade para agentes de IA e ferramentas de assistência de código que atuam neste repositório.

## 📱 Visão Geral do Projeto
O **MindfulDays** é um aplicativo Android nativo focado em mindfulness e saúde mental, baseado nas **9 Atitudes de Mindfulness de Jon Kabat-Zinn**, com suporte a reflexões diárias geradas via **Google Gemini API** e um timer circular de meditação.

## 🏗️ Padrões de Arquitetura e Stack Técnica
- **Linguagem**: Kotlin 1.9+ (uso idiomático de coroutines, StateFlow e imutabilidade).
- **UI Toolkit**: Jetpack Compose com Material Design 3 (M3). **Proibido o uso de layouts XML tradicionais**.
- **Padrão de Arquitetura**: MVVM (Model-View-ViewModel) alinhado com Clean Architecture:
  - `data/model/`: Data classes imutáveis.
  - `data/repository/`: Repositórios para fonte de dados local e lógica das 9 atitudes.
  - `data/remote/`: Clientes e serviços para comunicação com a Gemini API.
  - `ui/`: Telas e componentes em Compose divididos por feature (`home`, `timer`, `settings`).
  - `ui/theme/`: Tokens visuais (Color, Theme, Type).
  - `ui/viewmodel/`: ViewModels expondo estados via `StateFlow`.
- **Pacote Base**: `dev.mindfuldays.app`.

## 🎨 Design Tokens (Material Design 3)
Respeite estritamente a paleta de cores definida nos protótipos do Google Stitch:
- `SageGreen` (`#6B8E23`): Cor primária (tranquilidade, foco).
- `SageGreenLight` (`#8FA853`): Variante suave para destaques secundários.
- `SandMuted` (`#E8DFD8`): Cor secundária e divisores.
- `WarmOffWhite` (`#FBF9F5`): Fundo padrão das telas.
- `PureWhite` (`#FFFFFF`): Fundo de cards elevados e superfícies.
- `ForestDark` (`#2C3E35`): Texto principal e ícones de alto contraste.

## 🧠 Regras de Negócio Fundamentais
1. **Regra das 9 Atitudes**: A lista contém exatamente 9 atitudes fixas. O cálculo da atitude diária deve usar o dia do ano:
   `val index = (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1) % 9`
2. **Gemini API (Reflexões Diárias)**:
   - Utilizar o endpoint do modelo `gemini-1.5-flash:generateContent`.
   - Gerar reflexões concisas e inspiradoras com no máximo 2 frases para cada atitude.
   - Tratar ausência de chave de API e erros de rede com fallback amigável offline sem quebrar o app.

## 🛠️ Comandos de Verificação e Validação
Sempre verifique as alterações utilizando os seguintes comandos no terminal:
- **Compilação do app**: `./gradlew assembleDebug`
- **Testes Unitários**: `./gradlew testDebugUnitTest`
- **Linter & Análise Estática**: `./gradlew lintDebug`

## 🚫 Restrições Rígidas (Guardrails)
- **NUNCA** faça hardcode de credenciais ou chaves de API (`apiKey`) no código versionado.
- **NÃO** misture lógica de negócios ou chamadas assíncronas de rede diretamente dentro de funções `@Composable`.
- Mantenha funções Composable limpas e desacopladas, utilizando parâmetros para eventos e lambdas (`onAction: () -> Unit`) para facilitar testes de UI.
- Use nomes descritivos em português para textos de interface visível e inglês para código técnico, métodos e classes.

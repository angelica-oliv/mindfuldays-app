# 🧘 MindfulDays — Aplicativo Android de Mindfulness & IA

Bem-vindo ao repositório do aplicativo **MindfulDays**, desenvolvido durante o workshop prático de desenvolvimento Android com **Jetpack Compose**, **Material Design 3** e **Google Gemini API**.

---

## 🎯 Sobre o Aplicativo

O **MindfulDays** é um aplicativo minimalista e sereno de meditação guiada baseado nas **9 Atitudes de Mindfulness de Jon Kabat-Zinn**:
1. **Mente de Principiante**
2. **Não-Julgamento**
3. **Aceitação**
4. **Desapego**
5. **Confiança**
6. **Não-Esforço**
7. **Paciência**
8. **Gratidão**
9. **Generosidade**

### Principais Funcionalidades:
* **Atitude do Dia**: Rotação diária automática entre as 9 atitudes fundamentais com título e mensagem explicativa.
* **Reflexões com Gemini AI**: Geração de reflexões e conselhos inspiradores personalizados em tempo real através da API do Gemini (Google AI).
* **Timer de Meditação**: Contador circular regressivo minimalista de 10 minutos com controles de Play, Pause e Reset.
* **Configurações & Lembretes**: Gerenciamento de alertas diários e inserção segura de sua chave de API do Gemini.
* **Internacionalização (i18n)**: Suporte completo para Português (Brasil) e Inglês.

---

## 🌿 Paleta de Cores (Material Design 3)

| Token | Cor | Hex |
| :--- | :--- | :--- |
| **Primary** | Verde Sálvia | `#6B8E23` |
| **Primary Light** | Sálvia Claro | `#8FA853` |
| **Secondary** | Areia Muted | `#E8DFD8` |
| **Background** | Off-White Calmo | `#FBF9F5` |
| **Surface** | Branco Puro | `#FFFFFF` |
| **Text / OnSurface**| Verde Floresta Escuro | `#2C3E35` |

---

## 🧭 Branches para Acompanhamento Passo a Passo (Didática para Alunos)

Para facilitar o acompanhamento do workshop, cada etapa do desenvolvimento está salva em uma branch isolada e 100% funcional. Caso tenha alguma dificuldade durante a codificação, basta fazer o checkout da branch da etapa desejada:

```bash
# Etapa 1: Setup do projeto, Material Design 3, tema e tokens visuais
git checkout step-01-setup

# Etapa 2: Implementação das 9 Atitudes, Telas Compose, Timer e Gemini API
git checkout step-02-compose-logic

# Etapa 3: Testes Unitários, Testes de UI (Compose Test) e Pipeline CI/CD
git checkout step-03-testes-cicd

# Versão Completa e Consolidada
git checkout main
```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
* **Android Studio**: Versão Stable (ex: Ladybug, Jellyfish ou superior)
* **JDK**: Java 17 ou superior
* **Android SDK**: API 24 ou superior (Target: Android 15 / API 35+)

### Passos
1. Clone o repositório:
   ```bash
   git clone git@github.com:angelica-oliv/mindfuldays-app.git
   cd mindfuldays-app
   ```
2. Abra o projeto no **Android Studio**.
3. Aguarde a sincronização do Gradle.
4. Execute no emulador ou dispositivo físico através do botão **Run** (`Shift + F10`).

### Executando via Terminal:
* **Compilar o APK de Debug:**
  ```bash
  ./gradlew assembleDebug
  ```
* **Executar os Testes Unitários:**
  ```bash
  ./gradlew testDebugUnitTest
  ```

---

## 🤖 Configurando a Chave da Gemini API

1. Obtenha uma chave gratuita no [Google AI Studio](https://aistudio.google.com/).
2. Abra o aplicativo no seu celular ou emulador.
3. Clique no ícone de engrenagem ⚙️ no canto superior direito para abrir as **Configurações**.
4. Cole sua chave no campo **Chave de API do Gemini** e clique em **Salvar Chave**.
5. Volte para a Home e clique em **✨ Nova Reflexão (Gemini IA)**!

> *Nota: Caso execute o app sem uma chave configurada ou sem conexão de rede, o aplicativo entrará graciosamente em modo offline com reflexões locais.*

---

## 📄 Licença
Distribuído sob a licença MIT. Sinta-se livre para usar em workshops, palestras e estudos.

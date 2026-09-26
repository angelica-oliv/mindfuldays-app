package dev.mindfuldays.app.data.repository

import dev.mindfuldays.app.data.model.MindfulnessAttitude
import java.util.Calendar

class AttitudeRepository {

    val attitudes = listOf(
        MindfulnessAttitude(1, "Mente de Principiante", "Olhe para as coisas como se fosse a primeira vez, livre de expectativas."),
        MindfulnessAttitude(2, "Não-Julgamento", "Observe seus pensamentos e sentimentos sem rotulá-los como bons ou ruins."),
        MindfulnessAttitude(3, "Aceitação", "Reconheça e acolha o momento presente exatamente como ele é."),
        MindfulnessAttitude(4, "Desapego", "Deixe ir pensamentos e desejos que tentam prender sua atenção."),
        MindfulnessAttitude(5, "Confiança", "Desenvolva uma crença básica em si mesmo e nos seus sinais corporais."),
        MindfulnessAttitude(6, "Não-Esforço", "Abandone a necessidade constante de alcançar um resultado imediato."),
        MindfulnessAttitude(7, "Paciência", "Compreenda que certas coisas precisam de tempo para se desenvolver."),
        MindfulnessAttitude(8, "Gratidão", "Aprecie o momento presente e valorize o que você já possui."),
        MindfulnessAttitude(9, "Generosidade", "Ofereça atenção, carinho e presença com o coração aberto.")
    )

    fun getTodayAttitude(): MindfulnessAttitude {
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val index = (dayOfYear - 1) % attitudes.size
        return attitudes[index]
    }
}

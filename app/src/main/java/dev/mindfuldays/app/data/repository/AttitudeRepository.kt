package dev.mindfuldays.app.data.repository

import dev.mindfuldays.app.R
import dev.mindfuldays.app.data.model.MindfulnessAttitude
import java.util.Calendar

class AttitudeRepository {

    val attitudes = listOf(
        MindfulnessAttitude(1, "Mente de Principiante", "Olhe para as coisas como se fosse a primeira vez, livre de expectativas.", R.string.attitude_1_name, R.string.attitude_1_desc),
        MindfulnessAttitude(2, "Não-Julgamento", "Observe seus pensamentos e sentimentos sem rotulá-los como bons ou ruins.", R.string.attitude_2_name, R.string.attitude_2_desc),
        MindfulnessAttitude(3, "Aceitação", "Reconheça e acolha o momento presente exatamente como ele é.", R.string.attitude_3_name, R.string.attitude_3_desc),
        MindfulnessAttitude(4, "Desapego", "Deixe ir pensamentos e desejos que tentam prender sua atenção.", R.string.attitude_4_name, R.string.attitude_4_desc),
        MindfulnessAttitude(5, "Confiança", "Desenvolva uma crença básica em si mesmo e nos seus sinais corporais.", R.string.attitude_5_name, R.string.attitude_5_desc),
        MindfulnessAttitude(6, "Não-Esforço", "Abandone a necessidade constante de alcançar um resultado imediato.", R.string.attitude_6_name, R.string.attitude_6_desc),
        MindfulnessAttitude(7, "Paciência", "Compreenda que certas coisas precisam de tempo para se desenvolver.", R.string.attitude_7_name, R.string.attitude_7_desc),
        MindfulnessAttitude(8, "Gratidão", "Aprecie o momento presente e valorize o que você já possui.", R.string.attitude_8_name, R.string.attitude_8_desc),
        MindfulnessAttitude(9, "Generosidade", "Ofereça atenção, carinho e presença com o coração aberto.", R.string.attitude_9_name, R.string.attitude_9_desc)
    )

    fun getTodayAttitude(dayOfYear: Int = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)): MindfulnessAttitude {
        val index = (dayOfYear - 1).mod(attitudes.size)
        return attitudes[index]
    }
}

package dev.mindfuldays.app.data.repository

import dev.mindfuldays.app.R
import dev.mindfuldays.app.data.model.MindfulnessAttitude

// TODO: Implement 9 Jon Kabat-Zinn attitudes & daily rotation in Module 3
class AttitudeRepository {
    val attitudes = listOf(
        MindfulnessAttitude(
            id = 3,
            name = "Aceitação",
            description = "Reconheça e acolha o momento presente exatamente como ele é.",
            nameRes = R.string.attitude_3_name,
            descriptionRes = R.string.attitude_3_desc
        )
    )

    fun getTodayAttitude(): MindfulnessAttitude = attitudes.first()
}

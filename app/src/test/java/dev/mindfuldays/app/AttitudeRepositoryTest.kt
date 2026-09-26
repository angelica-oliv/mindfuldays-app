package dev.mindfuldays.app

import dev.mindfuldays.app.data.repository.AttitudeRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class AttitudeRepositoryTest {

    private lateinit var repository: AttitudeRepository

    @Before
    fun setUp() {
        repository = AttitudeRepository()
    }

    @Test
    fun `garantir que a lista possui exatamente 9 atitudes de mindfulness`() {
        val attitudes = repository.attitudes
        assertEquals(9, attitudes.size)
    }

    @Test
    fun `garantir que a atitude do dia retorna um valor valido dentro das 9 opções`() {
        val todayAttitude = repository.getTodayAttitude()
        assertNotNull(todayAttitude)
        assert(todayAttitude.id in 1..9)
    }
}

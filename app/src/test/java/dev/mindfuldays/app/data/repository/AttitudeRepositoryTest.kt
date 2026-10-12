package dev.mindfuldays.app.data.repository

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
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
    fun `garantir que todas as atitudes possuem IDs unicos e recursos de string validos`() {
        val attitudes = repository.attitudes
        val ids = attitudes.map { it.id }.toSet()
        assertEquals(9, ids.size)

        attitudes.forEach { attitude ->
            assertTrue(attitude.nameRes != 0)
            assertTrue(attitude.descriptionRes != 0)
            assertTrue(attitude.name.isNotBlank())
        }
    }

    @Test
    fun `garantir rotacao ciclica correta das 9 atitudes pelo dia do ano`() {
        // Dia 1 deve retornar a primeira atitude (id 1)
        assertEquals(1, repository.getTodayAttitude(dayOfYear = 1).id)

        // Dia 9 deve retornar a nona atitude (id 9)
        assertEquals(9, repository.getTodayAttitude(dayOfYear = 9).id)

        // Dia 10 deve reiniciar o ciclo na primeira atitude (id 1)
        assertEquals(1, repository.getTodayAttitude(dayOfYear = 10).id)

        // Dia 365 (ano comum): (365 - 1) % 9 = 364 % 9 = 4 -> índice 4 -> id 5
        assertEquals(5, repository.getTodayAttitude(dayOfYear = 365).id)
    }

    @Test
    fun `garantir que a chamada padrao de hoje retorna uma atitude valida`() {
        val today = repository.getTodayAttitude()
        assertNotNull(today)
        assertTrue(today.id in 1..9)
    }
}

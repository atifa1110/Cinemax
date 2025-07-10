package com.example.core.ui.mapper

import com.example.core.domain.model.CastModel
import com.example.core.domain.model.CreditsListModel
import com.example.core.domain.model.CrewModel
import com.example.core.ui.model.Cast
import com.example.core.ui.model.CreditsList
import com.example.core.ui.model.Crew
import org.junit.Assert.assertEquals
import org.junit.Test

class CreditsUiMapperTest {

    @Test
    fun `asCredits should map CreditsListModel to CreditsList correctly`() {
        // Given
        val castModel = CastModel(
            id = 1,
            name = "John Doe",
            character = "Hero",
            profilePath = "/path.jpg",
            adult = false,
            castId = 101,
            creditId = "credit123",
            gender = 1,
            knownForDepartment = "Acting",
            order = 0,
            originalName = "John D.",
            popularity = 9.5
        )

        val crewModel = CrewModel(
            id = 2,
            name = "Jane Smith",
            job = "Director",
            profilePath = "/crew.jpg",
            adult = false,
            creditId = "credit456",
            department = "Directing",
            gender = 2,
            knownForDepartment = "Directing",
            originalName = "Jane S.",
            popularity = 8.2
        )

        val creditsListModel = CreditsListModel(
            cast = listOf(castModel),
            crew = listOf(crewModel)
        )

        // When
        val result = creditsListModel.asCredits()

        // Then
        val expected = CreditsList(
            cast = listOf(Cast(id = 1, name = "John Doe", character = "Hero", profilePath = "/path.jpg")),
            crew = listOf(Crew(id = 2, name = "Jane Smith", job = "Director", profilePath = "/crew.jpg"))
        )

        assertEquals(expected, result)
    }
}

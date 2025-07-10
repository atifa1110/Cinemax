package com.example.core.data.mapper

import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.network.asImageURL
import com.example.core.data.network.model.movie.NetworkCast
import com.example.core.data.network.model.movie.NetworkCrew
import com.example.core.data.network.model.movie.NetworkListCredits

import kotlin.test.Test
import kotlin.test.assertEquals

class CreditMapperTest {
    private val sampleNetworkCast = NetworkCast(
        id = 101,
        name = "John Doe",
        adult = false,
        castId = 5,
        character = "Hero",
        creditId = "credit123",
        gender = 2,
        knownForDepartment = "Acting",
        order = 1,
        originalName = "Johnathan Doe",
        popularity = 99.9,
        profilePath = "/profile.jpg"
    )

    private val sampleNetworkCrew = NetworkCrew(
        id = 202,
        name = "Jane Smith",
        adult = false,
        creditId = "credit456",
        department = "Directing",
        gender = 1,
        job = "Director",
        knownForDepartment = "Directing",
        originalName = "Jane A. Smith",
        popularity = 88.8,
        profilePath = "/crew.jpg"
    )

    @Test
    fun `NetworkListCredits is mapped correctly to CreditsListEntity`() {
        val networkCredits = NetworkListCredits(
            cast = listOf(sampleNetworkCast),
            crew = listOf(sampleNetworkCrew)
        )

        val entity = networkCredits.asCredits()

        // Cast
        val cast = entity.cast[0]
        assertEquals(sampleNetworkCast.id, cast.id)
        assertEquals(sampleNetworkCast.name, cast.name)
        assertEquals(sampleNetworkCast.profilePath?.asImageURL(), cast.profilePath)

        // Crew
        val crew = entity.crew[0]
        assertEquals(sampleNetworkCrew.name, crew.name)
        assertEquals(sampleNetworkCrew.job, crew.job)
        assertEquals(sampleNetworkCrew.profilePath?.asImageURL(), crew.profilePath)
    }

    @Test
    fun `CreditsListEntity is mapped correctly to CreditsListModel`() {
        val entity = CreditsListEntity(
            cast = listOf(sampleNetworkCast.asCast()),
            crew = listOf(sampleNetworkCrew.asCrew())
        )

        val model = entity.asCreditsModel()

        // Cast
        val castModel = model.cast[0]
        assertEquals("John Doe", castModel.name)
        assertEquals("Hero", castModel.character)

        // Crew
        val crewModel = model.crew[0]
        assertEquals("Jane Smith", crewModel.name)
        assertEquals("Director", crewModel.job)
    }
}

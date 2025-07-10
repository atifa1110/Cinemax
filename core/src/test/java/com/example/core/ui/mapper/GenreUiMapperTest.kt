package com.example.core.ui.mapper

import com.example.core.R
import com.example.core.domain.model.GenreModel
import com.example.core.ui.model.Genre
import org.junit.Assert.assertEquals
import org.junit.Test

class GenreUiMapperTest {
    @Test
    fun `asGenres should map GenreModel list to Genre list correctly`() {
        // Given
        val input = listOf(GenreModel.ACTION, GenreModel.DRAMA)

        // When
        val result = input.asGenres()

        // Then
        val expected = listOf(
            Genre(nameResourceId = R.string.action),
            Genre(nameResourceId = R.string.drama)
        )

        assertEquals(expected, result)
    }

    @Test
    fun `asGenreModel should map Genre list back to GenreModel list`() {
        // Given
        val input = listOf(
            Genre(nameResourceId = R.string.fantasy),
            Genre(nameResourceId = R.string.horror)
        )

        // When
        val result = input.asGenreModel()

        // Then
        val expected = listOf(
            GenreModel.FANTASY,
            GenreModel.HORROR
        )

        assertEquals(expected, result)
    }
}
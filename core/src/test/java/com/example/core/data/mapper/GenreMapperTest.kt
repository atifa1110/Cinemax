package com.example.core.data.mapper

import com.example.core.data.local.model.movie.Genre
import com.example.core.data.local.model.movie.GenreEntity
import com.example.core.data.network.model.genre.GenreNetwork
import com.example.core.domain.model.GenreModel
import kotlin.test.Test
import kotlin.test.assertEquals

class GenreMapperTest {

    private val genreId = 28

    @Test
    fun `List of Int maps to List of GenreModel`() {
        val ids = listOf(genreId)
        val result = ids.asGenreModels()
        val expected = listOf(GenreModel.ACTION)
        assertEquals(expected, result)
    }

    @Test
    fun `GenreNetwork maps to Genre`() {
        val network = GenreNetwork(id = genreId, name = "Action")
        val result = network.asGenre()
        val expected = Genre.ACTION
        assertEquals(expected, result)
    }

    @Test
    fun `List of GenreNetwork maps to List of Genre`() {
        val networkList = listOf(GenreNetwork(id = genreId, name = "Action"))
        val result = networkList.asGenreNetwork()
        val expected = listOf(Genre.ACTION)
        assertEquals(expected, result)
    }

    @Test
    fun `Genre maps to GenreModel`() {
        val genre = Genre.ACTION
        val result = genre.asGenreModel()
        val expected = GenreModel.ACTION
        assertEquals(expected, result)
    }

    @Test
    fun `List of Genre maps to List of GenreModel`() {
        val genres = listOf(Genre.ACTION)
        val result = genres.asGenreModels()
        val expected = listOf(GenreModel.ACTION)
        assertEquals(expected, result)
    }

    @Test
    fun `GenreEntity maps to GenreModel`() {
        val entity = GenreEntity(name = "action")
        val result = entity.asGenreModel()
        val expected = GenreModel.ACTION
        assertEquals(expected, result)
    }

    @Test
    fun `List of GenreEntity maps to List of GenreModel`() {
        val entities = listOf(GenreEntity(name = "action"))
        val result = entities.asGenreModel()
        val expected = listOf(GenreModel.ACTION)
        assertEquals(expected, result)
    }

    @Test
    fun `GenreModel maps to GenreEntity`() {
        val model = GenreModel.ACTION
        val result = model.asGenreEntity()
        val expected = GenreEntity(name = "action")
        assertEquals(expected, result)
    }

    @Test
    fun `List of GenreModel maps to List of GenreEntity`() {
        val models = listOf(GenreModel.ACTION)
        val result = models.asGenreEntity()
        val expected = listOf(GenreEntity(name = "action"))
        assertEquals(expected, result)
    }

    @Test
    fun `List of Int maps to List of Genre`() {
        val ids = listOf(genreId)
        val result = ids.asGenres()
        val expected = listOf(Genre.ACTION)
        assertEquals(expected, result)
    }
}

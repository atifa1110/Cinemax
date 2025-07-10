package com.example.core.data.local.converter

import com.example.core.data.local.model.movie.Genre
import com.example.core.data.local.model.movie.GenreEntity
import com.example.core.data.local.model.tv.SeasonEntity
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ListConverterTest {

    private val listConverter = ListConverter()
    private val gson = Gson()

    @Test
    fun `fromGenreStringList - converts List String to JSON`() {
        val list = listOf("Action", "Comedy", "Drama")
        val json = listConverter.fromGenreStringList(list)
        assertEquals(gson.toJson(list), json)
    }

    @Test
    fun `toGenreStringList - converts JSON to List String`() {
        val list = listOf("Action", "Comedy", "Drama")
        val json = gson.toJson(list)
        val convertedList = listConverter.toGenreStringList(json)
        assertEquals(list, convertedList)
    }

    @Test
    fun `fromGenreIntList - converts List Int to JSON`() {
        val list = listOf(1, 2, 3)
        val json = listConverter.fromGenreIntList(list)
        assertEquals(gson.toJson(list), json)
    }

    @Test
    fun `toGenreIntList - converts JSON to List Int`() {
        val list = listOf(1, 2, 3)
        val json = gson.toJson(list)
        val convertedList = listConverter.toGenreIntList(json)
        assertEquals(list, convertedList)
    }

    @Test
    fun `fromGenreList - converts List Genre to JSON`() {
        val list = listOf(Genre.ACTION,Genre.ADVENTURE)
        val json = listConverter.fromGenreList(list)
        assertEquals(gson.toJson(list), json)
    }

    @Test
    fun `toGenreList - converts JSON to List Genre`() {
        val list = listOf(Genre.ACTION,Genre.ADVENTURE)
        val json = gson.toJson(list)
        val convertedList = listConverter.toGenreList(json)
        assertEquals(list, convertedList)
    }

    @Test
    fun `fromGenreEntityList - converts List GenreEntity to JSON`() {
        val list = listOf(GenreEntity(1, "Action"), GenreEntity(2, "Comedy"))
        val json = listConverter.fromGenreEntityList(list)
        assertEquals(gson.toJson(list), json)
    }

    @Test
    fun `toGenreEntityList - converts JSON to List GenreEntity`() {
        val list = listOf(GenreEntity(1, "Action"), GenreEntity(2, "Comedy"))
        val json = gson.toJson(list)
        val convertedList = listConverter.toGenreEntityList(json)
        assertEquals(list, convertedList)
    }

    @Test
    fun `fromSeasonEntityList - converts List SeasonEntity to JSON`() {
        val list = listOf(SeasonEntity("", 0,1,"Season 1", "",
            "",0,""), SeasonEntity("", 0,2,"Season 2", "",
            "",0,""))
        val json = listConverter.fromSeasonEntityList(list)
        assertEquals(gson.toJson(list), json)
    }

    @Test
    fun `toSeasonEntityList - converts JSON to List SeasonEntity`() {
        val list = listOf(SeasonEntity("", 0,1,"Season 1", "",
            "",0,""), SeasonEntity("", 0,2,"Season 2", "",
            "",0,""))
        val json = gson.toJson(list)
        val convertedList = listConverter.toSeasonEntityList(json)
        assertEquals(list, convertedList)
    }

    @Test
    fun `fromGenreStringList - handles null list`() {
        val json = listConverter.fromGenreStringList(null)
        assertNull(json)
    }

    @Test
    fun `toGenreStringList - handles null json`() {
        val list = listConverter.toGenreStringList(null)
        assertNull(list)
    }

    @Test
    fun `fromGenreIntList - handles null list`() {
        val json = listConverter.fromGenreIntList(null)
        assertNull(json)
    }

    @Test
    fun `toGenreIntList - handles null json`() {
        val list = listConverter.toGenreIntList(null)
        assertNull(list)
    }

    @Test
    fun `fromGenreList - handles null list`() {
        val json = listConverter.fromGenreList(null)
        assertNull(json)
    }

    @Test
    fun `toGenreList - handles null json`() {
        val list = listConverter.toGenreList(null)
        assertNull(list)
    }

    @Test
    fun `fromGenreEntityList - handles null list`() {
        val json = listConverter.fromGenreEntityList(null)
        assertNull(json)
    }

    @Test
    fun `toGenreEntityList - handles null json`() {
        val list = listConverter.toGenreEntityList(null)
        assertNull(list)
    }

    @Test
    fun `fromSeasonEntityList - handles null list`() {
        val json = listConverter.fromSeasonEntityList(null)
        assertNull(json)
    }

    @Test
    fun `toSeasonEntityList - handles null json`() {
        val list = listConverter.toSeasonEntityList(null)
        assertNull(list)
    }
}
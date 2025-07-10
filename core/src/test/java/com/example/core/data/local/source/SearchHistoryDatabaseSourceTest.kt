package com.example.core.data.local.source

import com.example.core.data.local.dao.search.SearchDao
import com.example.core.data.local.model.search.SearchEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchHistoryDatabaseSourceTest {

    private lateinit var searchDao: SearchDao
    private lateinit var source: SearchHistoryDatabaseSource

    @Before
    fun setup() {
        searchDao = mockk(relaxed = true)
        source = SearchHistoryDatabaseSource(searchDao)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    val entity = SearchEntity(id = 1, mediaType = "movie",
        overview = "", popularity = 0.0,
        genreEntities = emptyList(), title = "Inception", releaseDate = "", adult = false, originalTitle = "",
        originalLanguage = "", timestamp = 0, voteAverage = 0.0, voteCount = 0, posterPath = "", backdropPath = "",
        video = false, rating = 2.0, runtime = 120)


    val mockList = listOf(entity)


    @Test
    fun `getSearchHistory should return list of search entities`() = runTest {
        // Given
        every { searchDao.getSearchHistory() } returns flowOf(mockList)

        val result = source.getSearchHistory().first()

        assertEquals(1, result.size)
        assertEquals("Inception", result[0].title)
    }

    @Test
    fun `addMovieToSearchHistory should call insert on dao`() = runTest {
        // Given
        coEvery { searchDao.insertSearchHistory(entity) } just Runs

        // When
        source.addMovieToSearchHistory(entity)

        // Then
        coVerify { searchDao.insertSearchHistory(entity) }
    }

    @Test
    fun `deleteMovieToSearchHistory should call delete on dao`() = runTest {
        // Given
        val id = 3
        coEvery { searchDao.deleteSearchHistory(id) } just Runs

        // When
        source.deleteMovieToSearchHistory(id)

        // Then
        coVerify { searchDao.deleteSearchHistory(id) }
    }

    @Test
    fun `isSearchExist should return true if search exists`() = runTest {
        // Given
        val id = 1
        coEvery { searchDao.isSearchExist(id) } returns true

        // When
        val result = source.isSearchExist(id)

        // Then
        assertTrue(result)
        coVerify { searchDao.isSearchExist(id) }
    }
}
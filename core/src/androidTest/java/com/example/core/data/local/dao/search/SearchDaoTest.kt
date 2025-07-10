package com.example.core.data.local.dao.search

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.data.local.database.MovieDatabase
import com.example.core.data.local.model.search.SearchEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SearchDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var searchDao: SearchDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        searchDao = database.searchHistoryDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    val searchEntity = SearchEntity(
        id = 12444, mediaType = "Movie", title = "Harry Potter",
        overview = "overview", popularity = 0.0, releaseDate = "", adult = false,
        genreEntities = emptyList(), originalTitle = "", originalLanguage = "",
        voteAverage = 0.0, voteCount = 0, posterPath = "", backdropPath = "",
        video = false, rating = 3.9, runtime = 146, timestamp = 1735113324891
    )

    @Test
    fun insertSearchHistoryReturnGetHistory() = runTest{
        searchDao.insertSearchHistory(searchEntity)

        val history = searchDao.getSearchHistory().first() // Collect first emission
        assertEquals(1, history.size)
        assertEquals("Harry Potter", history[0].title)
    }

    @Test
    fun deleteSearchHistoryReturnGetHistory() = runTest {
        searchDao.insertSearchHistory(searchEntity)
        searchDao.deleteSearchHistory(12444)

        val history = searchDao.getSearchHistory().first()
        assertTrue(history.isEmpty())
    }

    @Test
    fun insertHistoryCheckIsSearchExist() = runTest {
        searchDao.insertSearchHistory(searchEntity)

        val exists = searchDao.isSearchExist(12444)
        assertTrue(exists)
    }

    @Test
    fun insertHistoryCheckIsSearchNotExist() = runTest {
        searchDao.insertSearchHistory(searchEntity)

        val notExists = searchDao.isSearchExist(12345)
        assertFalse(notExists)
    }
}
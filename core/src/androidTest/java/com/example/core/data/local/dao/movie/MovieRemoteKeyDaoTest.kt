package com.example.core.data.local.dao.movie

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.data.local.database.MovieDatabase
import com.example.core.data.local.model.movie.MovieRemoteKeyEntity
import com.example.core.data.local.util.DatabaseMediaType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieRemoteKeyDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var movieRemoteKeyDao: MovieRemoteKeyDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        movieRemoteKeyDao = database.movieRemoteKeyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndRetrieve() = runTest {
        val remoteKey = MovieRemoteKeyEntity(
            id = 1,
            mediaType = DatabaseMediaType.Movie.Popular,
            prevPage = 1,
            nextPage = 2
        )
        movieRemoteKeyDao.insertAll(listOf(remoteKey))

        val result = movieRemoteKeyDao.getByIdAndMediaType(1, DatabaseMediaType.Movie.Popular)
        assertEquals(remoteKey, result)
    }

    @Test
    fun testDeleteByMediaType() = runTest {
        val remoteKeys = listOf(
            MovieRemoteKeyEntity(
                id = 1,
                mediaType = DatabaseMediaType.Movie.Popular,
                prevPage = 1,
                nextPage = 2
            ),
            MovieRemoteKeyEntity(
                id = 2,
                mediaType = DatabaseMediaType.Movie.Trending,
                prevPage = 1,
                nextPage = 2
            )
        )
        movieRemoteKeyDao.insertAll(remoteKeys)
        movieRemoteKeyDao.deleteByMediaType(DatabaseMediaType.Movie.Popular)

        val result = movieRemoteKeyDao.getByIdAndMediaType(1, DatabaseMediaType.Movie.Popular)
        assertNull(result)
    }
}
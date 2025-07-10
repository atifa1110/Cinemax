package com.example.core.data.local.dao.movie

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.data.local.database.MovieDatabase
import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.local.model.detailMovie.ImagesListEntity
import com.example.core.data.local.model.detailMovie.MovieDetailsEntity
import com.example.core.data.local.model.detailMovie.VideosListEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieDetailsDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var movieDetailDao: MovieDetailsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        movieDetailDao = database.movieDetailsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    val movieDetail = MovieDetailsEntity(
        id = 845781,
        adult = false,
        backdropPath = "/cjEcqdRdPQJhYre3HUAc5538Gk8.jpg",
        budget = 0,
        genreEntities = emptyList(),
        homepage = "https://www.amazon.com/salp/redonemovie",
        imdbId = "tt14948432",
        originalLanguage = "en",
        originalTitle = "Red One",
        overview = "After Santa Claus (codename: Red One) is kidnapped",
        popularity = 4760.061,
        posterPath = "/cdqLnri3NEGcmfnqwk2TSIYtddg.jpg",
        releaseDate = "2024-10-31",
        revenue = 182861176,
        runtime = 124,
        status = "Released",
        tagline = "The mission to save Christmas is on.",
        title = "Red One",
        video = false,
        voteAverage = 7.032,
        voteCount = 1550,
        rating = 89.0,
        credits = CreditsListEntity(emptyList(), emptyList()),
        images = ImagesListEntity(emptyList(), emptyList()),
        videos = VideosListEntity(emptyList())
    )

    @Test
    fun insertAndValidateIsNotEmpty() = runTest {
        movieDetailDao.insert(movieDetail)

        val result = movieDetailDao.getById(845781).first()

        assertEquals(true, result?.title?.isNotEmpty())
    }

    @Test
    fun insertAndRetrieveData() = runTest {
        movieDetailDao.insert(movieDetail)

        val result = movieDetailDao.getById(845781).first()

        assertEquals("Red One", result?.title)
        assertEquals("Released", result?.status)
    }

    @Test
    fun deleteDetailsById() = runTest {
        movieDetailDao.insert(movieDetail)

        movieDetailDao.deleteById(845781)

        val result = movieDetailDao.getById(845781).first()

        assertNull(result)
    }

}
package com.example.core.data.local.dao.movie

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.data.local.database.MovieDatabase
import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.util.DatabaseMediaType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        movieDao = database.movieDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    val movie = MovieEntity(
        id  = 0, mediaType = DatabaseMediaType.Movie.Popular,
        networkId = 845781, title = "Red One" , overview = "overview",
        popularity= 4760.061, releaseDate ="2024-10-31", adult= false,
        genreIds= emptyList(), originalTitle = "Red One", originalLanguage = "en",
        voteAverage= 7.031, voteCount = 1516, posterPath = "/poster.jpg",
        backdropPath = "/back.jpg", video = false, rating = 70.0, runtime = 120
    )

    val trending = MovieEntity(
        id  = 0, mediaType = DatabaseMediaType.Movie.Trending,
        networkId = 845781, title = "Red One", overview = "overview",
        popularity= 4760.061, releaseDate ="2024-10-31", adult= false,
        genreIds= emptyList(), originalTitle = "Red One", originalLanguage = "en",
        voteAverage= 7.031, voteCount = 1516, posterPath = "/poster.jpg",
        backdropPath = "/back.jpg", video = false, rating = 70.0, runtime = 120
    )

    val listMovies = listOf(movie,trending)

    @Test
    fun insertMovieReturnGetMediaTypeIsNotEmpty() =  runTest {
        movieDao.insertAll(listMovies)

        val result = movieDao.getByMediaType(DatabaseMediaType.Movie.Popular, 10).first()

        assertEquals(true,result.isNotEmpty())
        assertEquals(1, result.size)
    }

    @Test
    fun insertMovieReturnGetMediaType() =  runTest {
        movieDao.insertAll(listMovies)

        val result = movieDao.getByMediaType(DatabaseMediaType.Movie.Popular, 10).first()

        assertEquals(result[0].title,"Red One")
        assertEquals(result.size,1)
    }

    @Test
    fun deleteMoviesByMediaTypeReturnGetMediaType() =  runTest {
        movieDao.insertAll(listMovies)

        // Act
        movieDao.deleteByMediaType(DatabaseMediaType.Movie.Popular)

        val popularData = movieDao.getByMediaType(DatabaseMediaType.Movie.Popular, 10).first()
        val trendingData = movieDao.getByMediaType(DatabaseMediaType.Movie.Trending, 10).first()

        // Assert
        assertEquals(0, popularData.size)
        assertEquals(popularData.isNotEmpty(), false)
        assertEquals(trendingData.isNotEmpty(), true)
        assert(popularData.isEmpty())
        assert(trendingData.isNotEmpty())
    }

    @Test
    fun insertMovieReturnPagingSource() = runTest {
        // Arrange
        movieDao.insertAll(listMovies)

        val pagingSource = movieDao.getPagingByMediaType(DatabaseMediaType.Movie.Popular)
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assert(loadResult is PagingSource.LoadResult.Page)
        val page = loadResult as PagingSource.LoadResult.Page
        assertEquals(1, page.data.size)
        assertEquals("Red One", page.data[0].title)
    }

}
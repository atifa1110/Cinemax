package com.example.core.data.local.dao.tv

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.data.local.database.MovieDatabase
import com.example.core.data.local.model.tv.TvShowEntity
import com.example.core.data.local.util.DatabaseMediaType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TvShowDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var tvShowDao: TvShowDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        tvShowDao = database.tvShowDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    val tvShow = TvShowEntity(
        id = 93405,
        mediaType = DatabaseMediaType.TvShow.Popular,
        networkId =  93405,
        name = "Squid Game",
        overview = "Hundreds of cash-strapped players",
        popularity = 3138.471,
        firstAirDate = "2021-09-17",
        genres = emptyList(),
        originalName = "오징어 게임",
        originalLanguage = "ko",
        originCountry =  listOf("KR"),
        voteAverage = 7.835,
        voteCount = 14299,
        posterPath = "/poster.jpg",
        backdropPath = "/back.jpg",
        seasons = 2,
        rating = 0.0
    )

    val trending = TvShowEntity(
        id =  91363,
        mediaType = DatabaseMediaType.TvShow.Trending,
        networkId =  91363,
        name = "What If...?",
        overview =  "Taking inspiration from the comic",
        popularity = 1670.462,
        firstAirDate = "2021-08-11",
        genres = emptyList(),
        originalName = "What If...?",
        originalLanguage = "en",
        originCountry =  listOf("US"),
        voteAverage = 7.835,
        voteCount = 14299,
        posterPath = "/poster.jpg",
        backdropPath = "/back.jpg",
        seasons = 5,
        rating = 0.0
    )

    val listTvs = listOf(tvShow,trending)
    @Test
    fun insertAndValidateIsNotEmpty() = runTest {
        tvShowDao.insertAll(listTvs)

        val result = tvShowDao.getByMediaType(DatabaseMediaType.TvShow.Popular, 10).first()

        assertEquals(true,result.isNotEmpty())
        assertEquals(1, result.size)
    }

    @Test
    fun insertAndRetrieveMovies() = runTest {
        tvShowDao.insertAll(listTvs)

        val result = tvShowDao.getByMediaType(DatabaseMediaType.TvShow.Popular, 10).first()

        assertEquals(result[0].name,"Squid Game")
        assertEquals(result.size,1)
    }


    @Test
    fun deleteMoviesByMediaType() = runTest {
        tvShowDao.insertAll(listTvs)

        // Act
        tvShowDao.deleteByMediaType(DatabaseMediaType.TvShow.Popular)

        val popularData = tvShowDao.getByMediaType(DatabaseMediaType.TvShow.Popular, 10).first()
        val trendingData = tvShowDao.getByMediaType(DatabaseMediaType.TvShow.Trending, 10).first()

        // Assert
        assertEquals(0, popularData.size)
        assertEquals(popularData.isNotEmpty(), false)
        assertEquals(trendingData.isNotEmpty(), true)
        assert(popularData.isEmpty())
        assert(trendingData.isNotEmpty())
    }

    @Test
    fun insertAndRetrievePagingSource() = runTest {
        tvShowDao.insertAll(listTvs)

        val pagingSource = tvShowDao.getPagingByMediaType(DatabaseMediaType.TvShow.Popular)
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
        assertEquals("Squid Game", page.data[0].name)
    }
}
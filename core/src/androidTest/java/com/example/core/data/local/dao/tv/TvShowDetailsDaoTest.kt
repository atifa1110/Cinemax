package com.example.core.data.local.dao.tv

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.data.local.database.MovieDatabase
import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.local.model.tv.TvShowDetailsEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TvShowDetailsDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var tvShowDetailsDao: TvShowDetailsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        tvShowDetailsDao = database.tvShowDetailsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    val tvShowDetails = TvShowDetailsEntity(
        id = 93405,
        name = "Squid Game",
        adult = false,
        backdropPath =  "/2meX1nMdScFOoV4370rqHWKmXhY.jpg",
        episodeRunTime = emptyList(),
        firstAirDate= "2021-09-17",
        genres = emptyList(),
        seasons = emptyList(),
        homepage = "https://www.netflix.com/title/81040344",
        inProduction = false,
        languages = emptyList(),
        lastAirDate = "2024-12-26",
        numberOfEpisodes = 0,
        numberOfSeasons = 0,
        originCountry = emptyList(),
        originalLanguage = "ko",
        originalName = "오징어 게임",
        overview =  "Hundreds of cash-strapped players a",
        popularity = 3138.471,
        posterPath = "/dDlEmu3EZ0Pgg93K2SVNLCjCSvE.jpg",
        status = "Returning Series",
        tagline = "Let the new games begin.",
        type =  "Scripted",
        voteAverage =  7.835,
        voteCount = 14333,
        credits = CreditsListEntity(emptyList(), emptyList()),
        rating = 80.0
    )

    @Test
    fun insertAndValidateIsNotEmpty() = runTest {
        tvShowDetailsDao.insert(tvShowDetails)
        val result = tvShowDetailsDao.getById(93405).first()
        assertEquals(true,result?.name?.isNotEmpty())
    }

    @Test
    fun insertAndRetrieveData() = runTest {
        tvShowDetailsDao.insert(tvShowDetails)
        val result = tvShowDetailsDao.getById(93405).first()
        assertEquals("Squid Game",result?.name)
        assertEquals("Returning Series",result?.status)
    }

    @Test
    fun deleteDetailsById() = runTest {
        tvShowDetailsDao.insert(tvShowDetails)
        tvShowDetailsDao.deleteById(93405)
        val result = tvShowDetailsDao.getById(93405).first()
        assertNull(result)
    }
}
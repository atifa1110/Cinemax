package com.example.core.data.repository.search

import app.cash.turbine.test
import com.example.core.data.local.model.search.SearchEntity
import com.example.core.data.local.source.SearchHistoryDatabaseSource
import com.example.core.data.mapper.asSearchEntity
import com.example.core.data.mapper.asSearchModel
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.SearchModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchHistoryRepositoryImplTest {

    private lateinit var repository: SearchHistoryRepositoryImpl
    private lateinit var searchDatabaseSource: SearchHistoryDatabaseSource

    @Before
    fun setUp() {
        searchDatabaseSource = mockk()
        repository = SearchHistoryRepositoryImpl(searchDatabaseSource)
    }

    @Test
    fun `getSearchHistory should emit loading and success states`() = runTest {
        val mockEntities = listOf(
            SearchEntity(
                id = 0, mediaType ="", title = "Title", overview = "",
                popularity = 0.0, releaseDate = "", adult = false, genreEntities = emptyList(),
                originalTitle = "", originalLanguage = "", voteAverage = 0.0,
                voteCount = 0, posterPath = "", backdropPath = "", video = false,
                rating = 0.0, runtime = 0, timestamp = 0
            ),
            SearchEntity(
                id = 1, mediaType ="", title = "Title 2", overview = "",
                popularity = 0.0, releaseDate = "", adult = false, genreEntities = emptyList(),
                originalTitle = "", originalLanguage = "", voteAverage = 0.0,
                voteCount = 0, posterPath = "", backdropPath = "", video = false,
                rating = 0.0, runtime = 0, timestamp = 0
            ),
        )
        val mockModels = mockEntities.map { it.asSearchModel() }

        coEvery {searchDatabaseSource.getSearchHistory()} returns flowOf(mockEntities)

        repository.getSearchHistory().test {
            assertEquals(CinemaxResponse.Loading, awaitItem())
            assertEquals(CinemaxResponse.Success(mockModels), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getSearchHistory should emit failure when exception occurs`() = runTest {
        coEvery { searchDatabaseSource.getSearchHistory() } throws RuntimeException("Database Error")

        repository.getSearchHistory().test {
            assertEquals(CinemaxResponse.Loading, awaitItem())
            val failure = awaitItem()
            assertTrue(failure is CinemaxResponse.Failure)
            assertEquals("Database Error", failure.error)
            awaitComplete()
        }
    }


    @Test
    fun `addMovieToSearchHistory should call database source`() = runTest {
        val searchModel = SearchModel(
            id = 0, mediaType ="", title = "Title", overview = "",
            popularity = 0.0, releaseDate = "", adult = false, genres = emptyList(),
            originalTitle = "", originalLanguage = "", voteAverage = 0.0,
            voteCount = 0, posterPath = "", backdropPath = "", video = false,
            rating = 0.0, runtime = 0, timestamp = 0, profilePath = ""
        )
        coEvery { searchDatabaseSource.addMovieToSearchHistory(searchModel.asSearchEntity()) } just Runs
        repository.addMovieToSearchHistory(searchModel)
        coVerify { searchDatabaseSource.addMovieToSearchHistory(searchModel.asSearchEntity()) }
    }

    @Test
    fun `removeMovieFromSearchHistory should call database source`() = runTest {
        val movieId = 1
        coEvery{searchDatabaseSource.deleteMovieToSearchHistory(movieId)} just Runs
        repository.removeMovieFromSearchHistory(movieId)
        coVerify{searchDatabaseSource.deleteMovieToSearchHistory(movieId)}
    }

    @Test
    fun `isSearchExist should return expected result`() = runTest {
        val movieId = 1
        coEvery { searchDatabaseSource.isSearchExist(movieId) } returns true

        val result = repository.isSearchExist(movieId)

        assertTrue(result)
        coVerify { searchDatabaseSource.isSearchExist(movieId) }
    }
}
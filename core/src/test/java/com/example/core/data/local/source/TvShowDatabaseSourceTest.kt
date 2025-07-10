package com.example.core.data.local.source

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.example.core.data.local.dao.tv.TvShowDao
import com.example.core.data.local.dao.tv.TvShowRemoteKeyDao
import com.example.core.data.local.model.tv.TvShowEntity
import com.example.core.data.local.model.tv.TvShowRemoteKeyEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.repository.utils.FakeTransactionProvider
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class TvShowDatabaseSourceTest {

    private lateinit var tvShowDao: TvShowDao
    private lateinit var remoteKeyDao: TvShowRemoteKeyDao
    private lateinit var transactionProvider: FakeTransactionProvider
    private lateinit var source: TvShowDatabaseSource

    @Before
    fun setup() {
        tvShowDao = mockk(relaxed = true)
        remoteKeyDao = mockk(relaxed = true)
        transactionProvider = mockk()
        source = TvShowDatabaseSource(tvShowDao,remoteKeyDao,transactionProvider)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `updateTvSeasons should call DAO with correct params`() = runTest {
        coEvery { tvShowDao.updateSeasons(1,2) } just Runs
        source.updateTvSeasons(1, 2)
        coVerify {  tvShowDao.updateSeasons(1,2) }
    }

    @Test
    fun `getByMediaType should return tv from tvShowDao`() = runTest {
        // Given
        val mockTvList = listOf(
            TvShowEntity(
                id = 0, mediaType = DatabaseMediaType.TvShow.Popular, networkId = 93405,
                name = "Squid Game", overview = "", popularity = 0.0, firstAirDate = "",
                genres = listOf(), originalName = "오징어 게임", originalLanguage = "ko",
                originCountry = listOf(), voteAverage = 0.0, voteCount = 0, posterPath = "",
                backdropPath = "", seasons = 0, rating = 0.0
            ),
            TvShowEntity(
                id = 1 , mediaType = DatabaseMediaType.TvShow.Popular, networkId = 1399,
                name = "Game of Thrones", overview = "", popularity = 0.0,
                firstAirDate = "", genres = listOf(), originalName = "Game of Thrones",
                originalLanguage = "en", originCountry = listOf(), voteAverage = 0.0,
                voteCount = 0, posterPath = "", backdropPath = "", seasons = 0, rating = 0.0
            ),
        )

        coEvery { tvShowDao.getByMediaType(any(), any()) } returns flowOf(mockTvList)

        // When
        val result = source.getByMediaType(DatabaseMediaType.TvShow.Popular, 10).first()

        // Then
        assertEquals(mockTvList, result)
        assertEquals(2, result.size)
    }

    @Test
    fun `getByMediaType should return empty list when dao returns nothing`() = runTest {
        // Given
        coEvery { tvShowDao.getByMediaType(any(), any()) } returns flowOf(emptyList())

        // When
        val result = source.getByMediaType(DatabaseMediaType.TvShow.Popular, 10).first()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getPagingByMediaType should return PagingSource from tvDao`() = runTest {
        // Given
        val mockList = listOf(
            TvShowEntity(
                id = 0, mediaType = DatabaseMediaType.TvShow.Popular, networkId = 93405,
                name = "Squid Game", overview = "", popularity = 0.0, firstAirDate = "",
                genres = listOf(), originalName = "오징어 게임", originalLanguage = "ko",
                originCountry = listOf(), voteAverage = 0.0, voteCount = 0, posterPath = "",
                backdropPath = "", seasons = 0, rating = 0.0
            )
        )

        val fakePagingSource = object : PagingSource<Int, TvShowEntity>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowEntity> {
                return LoadResult.Page(
                    data = mockList,
                    prevKey = null,
                    nextKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<Int, TvShowEntity>): Int? = null
        }

        coEvery { tvShowDao.getPagingByMediaType(DatabaseMediaType.TvShow.Popular) } returns fakePagingSource

        // When
        val result = source.getPagingByMediaType(DatabaseMediaType.TvShow.Popular)
        val loadResult = result.load(
            LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Then
        assertTrue(loadResult is LoadResult.Page)
        val page = loadResult as LoadResult.Page
        assertEquals(mockList, page.data)
        assertEquals(null, page.prevKey)
        assertEquals(null, page.nextKey)
    }

    @Test
    fun `deleteByMediaTypeAndInsertAll should call delete and insert methods`() = runTest {
        // Arrange
        val mediaType = DatabaseMediaType.TvShow.Popular
        val newMovies = listOf(
            TvShowEntity(
                id = 0, mediaType = DatabaseMediaType.TvShow.Popular, networkId = 93405,
                name = "Squid Game", overview = "", popularity = 0.0, firstAirDate = "",
                genres = listOf(), originalName = "오징어 게임", originalLanguage = "ko",
                originCountry = listOf(), voteAverage = 0.0, voteCount = 0, posterPath = "",
                backdropPath = "", seasons = 0, rating = 0.0
            ),
            TvShowEntity(
                id = 1 , mediaType = DatabaseMediaType.TvShow.Popular, networkId = 1399,
                name = "Game of Thrones", overview = "", popularity = 0.0,
                firstAirDate = "", genres = listOf(), originalName = "Game of Thrones",
                originalLanguage = "en", originCountry = listOf(), voteAverage = 0.0,
                voteCount = 0, posterPath = "", backdropPath = "", seasons = 0, rating = 0.0
            ),
        )

        val transactionSlot = slot<suspend () -> Unit>()

        coEvery { transactionProvider.runWithTransaction(capture(transactionSlot)) } just Runs
        coEvery { tvShowDao.deleteByMediaType(any()) } just Runs
        coEvery { tvShowDao.insertAll(any()) } just Runs

        // Act
        source.deleteByMediaTypeAndInsertAll(mediaType, newMovies)

        // Manually invoke captured transaction block
        transactionSlot.captured.invoke()

        coVerify { tvShowDao.deleteByMediaType(mediaType) }
        coVerify { tvShowDao.insertAll(newMovies) }
    }

    @Test
    fun `getRemoteKeyByIdAndMediaType should return correct remote key`() = runTest {
        // Given
        val id = 1
        val mediaType = DatabaseMediaType.TvShow.Popular
        val expectedKey = TvShowRemoteKeyEntity(
            id = 1,
            mediaType = mediaType,
            prevPage = null,
            nextPage = 2
        )
        coEvery { remoteKeyDao.getByIdAndMediaType(id, mediaType) } returns expectedKey

        // When
        val result = source.getRemoteKeyByIdAndMediaType(id, mediaType)

        // Then
        assertEquals(expectedKey, result)
        coVerify { remoteKeyDao.getByIdAndMediaType(id, mediaType) }
    }
}
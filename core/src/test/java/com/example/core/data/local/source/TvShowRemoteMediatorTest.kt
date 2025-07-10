package com.example.core.data.local.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.core.data.local.model.tv.TvShowEntity
import com.example.core.data.local.model.tv.TvShowRemoteKeyEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.mapper.asNetworkMediaType
import com.example.core.data.network.datasource.TvShowNetworkDataSource
import com.example.core.data.network.model.movie.NetworkListCredits
import com.example.core.data.network.model.tv.TvShowDetailNetwork
import com.example.core.data.network.model.tv.TvShowNetwork
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.response.TvShowResponse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalPagingApi::class)
class TvShowRemoteMediatorTest {

    private lateinit var networkDataSource: TvShowNetworkDataSource
    private lateinit var databaseSource: TvShowDatabaseSource
    private lateinit var mediator: TvShowRemoteMediator

    private val mediaType = DatabaseMediaType.TvShow.Popular

    @Before
    fun setup() {
        networkDataSource = mockk()
        databaseSource = mockk()
        mediator = TvShowRemoteMediator(
            databaseDataSource = databaseSource,
            networkDataSource = networkDataSource,
            mediaType = mediaType
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    val mockNetworkTv = listOf(
        TvShowNetwork(
            id = 1, name = "Squid Game", overview = "", popularity = 0.0, firstAirDate = "",
            genreIds = listOf(), originalName = "오징어 게임", originalLanguage = "ko",
            originCountry = listOf(), voteAverage = 0.0, voteCount = 0, posterPath = "",
            backdropPath = ""
        )
    )

    val mockTv = listOf(
        TvShowEntity(
            id = 1, mediaType = DatabaseMediaType.TvShow.Popular, networkId = 1,
            name = "Squid Game", overview = "", popularity = 0.0, firstAirDate = "",
            genres = listOf(), originalName = "오징어 게임", originalLanguage = "ko",
            originCountry = listOf(), voteAverage = 0.0, voteCount = 0, posterPath = "",
            backdropPath = "", seasons = 0, rating = 0.0
        )
    )

    val mockTvDetail = TvShowDetailNetwork(
        id = 1,
        name = "Squid Game",
        adult = false,
        backdropPath = "/back.jpg",
        createdBy = emptyList(),
        credits = NetworkListCredits(emptyList(), emptyList()),
        episodeRunTime = emptyList(),
        firstAirDate = "2021-09-17",
        numberOfEpisodes = 16,
        numberOfSeasons = 3,
        voteAverage = 7.8,
        voteCount = 14677,
    )

    private fun createPagingState(entity: List<TvShowEntity>): PagingState<Int, TvShowEntity> {
        return PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = entity,
                    prevKey = null,
                    nextKey = 2
                )
            ),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )
    }

    val mockResponse = CinemaxResponse.Success(TvShowResponse(page = 1, results = mockNetworkTv, totalPages = 2, totalResults = 5))

    @Test
    fun `load REFRESH returns Success when data is returned`() = runTest {
        // Given
        val remoteKey = TvShowRemoteKeyEntity(id = 1, mediaType = mediaType, prevPage = null, nextPage = 2)
        val mockDetailResponse = CinemaxResponse.Success(mockTvDetail)
        val state = createPagingState(mockTv)

        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns remoteKey
        coEvery { networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 1) } returns mockResponse
        coEvery { databaseSource.handlePaging(eq(mediaType), any(), any(), eq(true)) } just Runs
        coEvery { networkDataSource.getDetailTv(any(), any()) } returns mockDetailResponse
        coEvery { databaseSource.updateTvSeasons(any(), any()) } just Runs

        // When
        val result = mediator.load(LoadType.REFRESH, state)

        // Then
        assert(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load PREPEND returns Success when previous page is available`() = runTest {
        val remoteKey = TvShowRemoteKeyEntity(id = 1, mediaType = mediaType,
            prevPage = 1, nextPage = 3)

        val mockDetailResponse = CinemaxResponse.Success(mockTvDetail)
        val state = createPagingState(mockTv)

        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns remoteKey
        coEvery { networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 1) } returns mockResponse
        coEvery { databaseSource.handlePaging(eq(mediaType), any(), any(), eq(false)) } just Runs
        coEvery { networkDataSource.getDetailTv(any(), any()) } returns mockDetailResponse
        coEvery { databaseSource.updateTvSeasons(any(), any()) } just Runs

        val result = mediator.load(LoadType.PREPEND, state)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse(result.endOfPaginationReached)
    }

    @Test
    fun `load APPEND returns Success when next page is available`() = runTest {
        val remoteKey = TvShowRemoteKeyEntity(
            id = 1, mediaType = mediaType, prevPage = 2, nextPage = 3)

        val mockResponse = CinemaxResponse.Success(
            TvShowResponse(page = 3, results = mockNetworkTv, totalPages = 4, totalResults = 10)
        )

        val mockDetailResponse = CinemaxResponse.Success(mockTvDetail)
        val state = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = mockTv,
                    prevKey = 2,
                    nextKey = 3
                )
            ),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(1, mediaType) } returns remoteKey
        coEvery { networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 3) } returns mockResponse
        coEvery { databaseSource.handlePaging(mediaType, any(), any(), shouldDeleteMoviesAndRemoteKeys = false) } just Runs
        coEvery { networkDataSource.getDetailTv(any(), any()) } returns mockDetailResponse
        coEvery { databaseSource.updateTvSeasons(any(),any()) } just Runs

        val result = mediator.load(LoadType.APPEND, state)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse(result.endOfPaginationReached)
    }

    @Test
    fun `load REFRESH returns endOfPaginationReached true when API returns empty`() = runTest {
        // Given
        val mockTv = emptyList<TvShowEntity>()
        val state = createPagingState(mockTv)
        val mockResponse = CinemaxResponse.Success(TvShowResponse(page = 1, results = emptyList(), totalPages = 2, totalResults = 5))
        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns null
        coEvery { networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 1) } returns mockResponse
        coEvery { databaseSource.handlePaging(mediaType, mockTv, any(), true) } just Runs

        // When
        val result = mediator.load(LoadType.REFRESH, state)

        // Then
        assert(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load PREPEND returns Success with endOfPaginationReached true when no remote key`() = runTest {
        // Given
        val mockTv = emptyList<TvShowEntity>()
        val state = createPagingState(mockTv)
        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns null

        // When
        val result = mediator.load(LoadType.PREPEND, state)

        // Then
        assert(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load REFRESH returns Error when networkDataSource returns Failure`() = runTest {
        // Given
        val mockTv = emptyList<TvShowEntity>()
        val state = createPagingState(mockTv)
        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns null
        coEvery {
            networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 1)
        } returns CinemaxResponse.Failure(404,"Network error")

        // When
        val result = mediator.load(LoadType.REFRESH, state)

        // Then
        assert(result is RemoteMediator.MediatorResult.Error)
        assertEquals("Network error", (result as RemoteMediator.MediatorResult.Error).throwable.message)
    }

    @Test
    fun `load REFRESH returns Error when database handlePaging throws exception`() = runTest {
        // Given
        val mockTv = emptyList<TvShowEntity>()
        val state = createPagingState(mockTv)
        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns null
        coEvery { networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 1) } returns mockResponse
        coEvery { databaseSource.handlePaging(eq(mediaType), any(), any(), eq(true))
        } throws IllegalStateException("Database insert error")

        // When
        val result = mediator.load(LoadType.REFRESH, state)

        // Then
        assert(result is RemoteMediator.MediatorResult.Error)
        assertEquals("Database insert error",(result as RemoteMediator.MediatorResult.Error).throwable.message)
    }

    @Test
    fun `load REFRESH returns Success even if updateTvSeasons throws error`() = runTest {
        // Given
        val state = createPagingState(mockTv)
        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns null
        coEvery { networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 1) } returns mockResponse
        coEvery { databaseSource.handlePaging(eq(mediaType), any(), any(), eq(true)) } just Runs
        coEvery { networkDataSource.getDetailTv(any(), any()) } returns CinemaxResponse.Failure(400,"detail error")

        // When
        val result = mediator.load(LoadType.REFRESH, state)

        // Then
        assert(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }
}
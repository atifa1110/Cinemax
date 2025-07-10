package com.example.core.data.local.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.model.movie.MovieRemoteKeyEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.mapper.asNetworkMediaType
import com.example.core.data.network.datasource.MovieNetworkDataSource
import com.example.core.data.network.model.movie.MovieDetailNetwork
import com.example.core.data.network.model.movie.MovieNetwork
import com.example.core.data.network.model.movie.NetworkListCredits
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.response.MovieResponse
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
class MovieRemoteMediatorTest {

    private lateinit var networkDataSource: MovieNetworkDataSource
    private lateinit var databaseSource: MovieDatabaseSource
    private lateinit var mediator: MovieRemoteMediator

    private val mediaType = DatabaseMediaType.Movie.Popular

    @Before
    fun setup() {
        networkDataSource = mockk()
        databaseSource = mockk()
        mediator = MovieRemoteMediator(
            databaseDataSource = databaseSource,
            networkDataSource = networkDataSource,
            mediaType = mediaType
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    val mockNetworkMovies = listOf(
        MovieNetwork(id = 1, title = "Mock Movie", overview = "",
            popularity = 0.0, releaseDate = "", adult = false, genreIds = listOf(), originalTitle = "",
            originalLanguage = "en", voteAverage = 0.0, voteCount = 0, posterPath = "", backdropPath = "",
            video = false)
    )

    val mockMovies = listOf(
        MovieEntity(id = 1, mediaType = mediaType, networkId = 1, title = "Mock Movie", overview = "",
            popularity = 0.0, releaseDate = "", adult = false, genreIds = listOf(), originalTitle = "",
            originalLanguage = "en", voteAverage = 0.0, voteCount = 0, posterPath = "", backdropPath = "",
            video = false, rating = 0.0, runtime = 0)
    )

    val movieDetail = MovieDetailNetwork(
        id = 1, adult = false, backdropPath = "", budget = 0,
        genres = listOf(), homepage = "", imdbId = "tt0241527", originalLanguage = "",
        originalTitle =  "Mock Movie", overview = "",
        popularity = 0.0, posterPath = "", releaseDate = "", revenue = 0,
        runtime = 10, status = "", tagline = "",
        title =  "Mock Movie",
        video = false, voteAverage = 0.0, voteCount = 0,
        credits = NetworkListCredits(listOf(), listOf()),
    )

    private fun createPagingState(entity: List<MovieEntity>): PagingState<Int, MovieEntity> {
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

    val mockResponse = CinemaxResponse.Success(MovieResponse(page = 1, results = mockNetworkMovies))

    @Test
    fun `load REFRESH returns Success when data is returned`() = runTest {
        // Given
        val mockDetailResponse = CinemaxResponse.Success(movieDetail)
        val state = createPagingState(mockMovies)

        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns null
        coEvery { networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 1) } returns mockResponse
        coEvery { databaseSource.handlePaging(eq(mediaType), any(), any(), eq(true)) } just Runs
        coEvery { networkDataSource.getDetailMovie(any(), any()) } returns mockDetailResponse
        coEvery { databaseSource.updateMovieRuntime(any(), any()) } just Runs

        // When
        val result = mediator.load(LoadType.REFRESH, state)

        // Then
        assert(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load APPEND returns Success when next page is available`() = runTest {
        // Given
        val remoteKey = MovieRemoteKeyEntity(
            id = 1, mediaType = mediaType, prevPage = 2, nextPage = 3)

        val mockResponse = CinemaxResponse.Success(
            MovieResponse(page = 3, results = mockNetworkMovies, totalPages = 4, totalResults = 10)
        )

        val mockDetailResponse = CinemaxResponse.Success(movieDetail)
        val state = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = mockMovies,
                    prevKey = 2,
                    nextKey = 3
                )
            ),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns remoteKey
        coEvery { networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 3) } returns mockResponse
        coEvery { databaseSource.handlePaging(eq(mediaType), any(), any(), eq(false)) } just Runs
        coEvery { networkDataSource.getDetailMovie(any(), any()) } returns mockDetailResponse
        coEvery { databaseSource.updateMovieRuntime(any(), any()) } just Runs

        // When
        val result = mediator.load(LoadType.APPEND, state)

        // Then
        assert(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load PREPEND returns Success when previous page is available`() = runTest {
        // Given
        val remoteKey = MovieRemoteKeyEntity(
            id = 1, mediaType = mediaType, prevPage = 1, nextPage = 3)
        val state = createPagingState(mockMovies)
        val mockDetailResponse = CinemaxResponse.Success(movieDetail)

        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns remoteKey
        coEvery { networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 1) } returns mockResponse
        coEvery { databaseSource.handlePaging(eq(mediaType), any(), any(), eq(false)) } just Runs
        coEvery { networkDataSource.getDetailMovie(any(), any()) } returns mockDetailResponse
        coEvery { databaseSource.updateMovieRuntime(any(), any()) } just Runs

        // When
        val result = mediator.load(LoadType.PREPEND, state)

        // Then
        assert(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load REFRESH returns endOfPaginationReached true when API returns empty`() = runTest {
        // Given
        val mockMovies = emptyList<MovieEntity>()
        val state = createPagingState(mockMovies)
        val mockResponse = CinemaxResponse.Success(MovieResponse(page = 1, results = emptyList()))
        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns null
        coEvery { networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 1) } returns mockResponse
        coEvery { databaseSource.handlePaging(mediaType, mockMovies, any(), true) } just Runs

        // When
        val result = mediator.load(LoadType.REFRESH, state)

        // Then
        assert(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load PREPEND returns Success with endOfPaginationReached true when no remote key`() = runTest {
        // Given
        val state = createPagingState(mockMovies)
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
        val state = createPagingState(mockMovies)
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
        val state = createPagingState(mockMovies)
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
    fun `load REFRESH returns Success even if updateMovieRuntime throws error`() = runTest {
        // Given
        val state = createPagingState(mockMovies)
        coEvery { databaseSource.getRemoteKeyByIdAndMediaType(any(), mediaType) } returns null
        coEvery { networkDataSource.getByMediaType(mediaType.asNetworkMediaType(), 1) } returns mockResponse
        coEvery { databaseSource.handlePaging(eq(mediaType), any(), any(), eq(true)) } just Runs
        coEvery { networkDataSource.getDetailMovie(any(), any()) } returns CinemaxResponse.Failure(400,"detail error")

        // When
        val result = mediator.load(LoadType.REFRESH, state)

        // Then
        assert(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

}
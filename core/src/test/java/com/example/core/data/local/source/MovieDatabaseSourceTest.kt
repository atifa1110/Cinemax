package com.example.core.data.local.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.local.dao.movie.MovieDao
import com.example.core.data.local.dao.movie.MovieRemoteKeyDao
import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.model.movie.MovieRemoteKeyEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.repository.utils.FakeTransactionProvider
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
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

class MovieDatabaseSourceTest {

    private lateinit var movieDao: MovieDao
    private lateinit var remoteKeyDao: MovieRemoteKeyDao
    private lateinit var transactionProvider: FakeTransactionProvider
    private lateinit var source: MovieDatabaseSource

    @Before
    fun setup() {
        movieDao = mockk(relaxed = true)
        remoteKeyDao = mockk(relaxed = true)
        transactionProvider = mockk()
        source = MovieDatabaseSource(movieDao, remoteKeyDao, transactionProvider)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `updateMovieRuntime should call DAO with correct params`() = runTest {
        coEvery { movieDao.updateRuntime(1, 120) } just Runs
        source.updateMovieRuntime(1, 120)
        coVerify { movieDao.updateRuntime(1, 120) }
    }

    @Test
    fun `getByMediaType should return movies from movieDao`() = runTest {
        // Given
        val mockMovieList = listOf(
            MovieEntity(
                id = 1, mediaType = DatabaseMediaType.Movie.Popular, networkId = 1, title = "Harry Potter and the Philosopher's Stone",
                overview = "", popularity = 234.43, releaseDate = "2001-11-16", adult = false, genreIds = listOf(1,2),
                originalTitle = "Harry Potter and the Philosopher's Stone", originalLanguage = "en",
                voteAverage = 7.9, voteCount = 27593, posterPath = "", backdropPath = "", video = false,
                rating = 0.0, runtime = 0
            ),
            MovieEntity(
                id = 2,mediaType = DatabaseMediaType.Movie.Popular, networkId = 2, title = "Sonic the Hedgehog 3",
                overview = "", popularity = 4014.717, releaseDate = "2024-12-19", adult = false, genreIds = listOf(2),
                originalTitle = "Sonic the Hedgehog 3", originalLanguage = "en", voteAverage = 7.774,
                voteCount = 1626, posterPath = "", backdropPath = "", video = false, rating = 0.0, runtime = 0
            )
        )

        coEvery { movieDao.getByMediaType(any(), any()) } returns flowOf(mockMovieList)

        // When
        val result = source.getByMediaType(DatabaseMediaType.Movie.Popular, 10).first()

        // Then
        assertEquals(mockMovieList, result)
        assertEquals(2, result.size)
    }

    @Test
    fun `getByMediaType should return empty list when dao returns nothing`() = runTest {
        // Given
        coEvery { movieDao.getByMediaType(any(), any()) } returns flowOf(emptyList())

        // When
        val result = source.getByMediaType(DatabaseMediaType.Movie.Popular, 10).first()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getPagingByMediaType should return PagingSource from movieDao`() = runTest {
        // Given
        val mockList = listOf(
            MovieEntity(
                id = 1, mediaType = DatabaseMediaType.Movie.Popular, networkId = 1, title = "Harry Potter",
                overview = "", popularity = 100.0, releaseDate = "2001-11-16", adult = false, genreIds = listOf(),
                originalTitle = "", originalLanguage = "en", voteAverage = 7.5, voteCount = 1000,
                posterPath = "", backdropPath = "", video = false, rating = 0.0, runtime = 0
            )
        )

        val fakePagingSource = object : PagingSource<Int, MovieEntity>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
                return LoadResult.Page(
                    data = mockList,
                    prevKey = null,
                    nextKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? = null
        }

        coEvery { movieDao.getPagingByMediaType(DatabaseMediaType.Movie.Popular) } returns fakePagingSource

        // When
        val result = source.getPagingByMediaType(DatabaseMediaType.Movie.Popular)
        val loadResult = result.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Then
        assertTrue(loadResult is PagingSource.LoadResult.Page)
        val page = loadResult as PagingSource.LoadResult.Page
        assertEquals(mockList, page.data)
        assertEquals(null, page.prevKey)
        assertEquals(null, page.nextKey)
    }

    @Test
    fun `deleteByMediaTypeAndInsertAll should call delete and insert methods`() = runTest {
        // Arrange
        val mediaType = DatabaseMediaType.Movie.Popular
        val newMovies = listOf(
            MovieEntity(
                id = 1, mediaType = DatabaseMediaType.Movie.Popular, networkId = 1, title = "Harry Potter and the Philosopher's Stone",
                overview = "", popularity = 234.43, releaseDate = "2001-11-16", adult = false, genreIds = listOf(1,2),
                originalTitle = "Harry Potter and the Philosopher's Stone", originalLanguage = "en",
                voteAverage = 7.9, voteCount = 27593, posterPath = "", backdropPath = "", video = false,
                rating = 0.0, runtime = 0
            ),
            MovieEntity(
                id = 2,mediaType = DatabaseMediaType.Movie.Popular, networkId = 2, title = "Sonic the Hedgehog 3",
                overview = "", popularity = 4014.717, releaseDate = "2024-12-19", adult = false, genreIds = listOf(2),
                originalTitle = "Sonic the Hedgehog 3", originalLanguage = "en", voteAverage = 7.774,
                voteCount = 1626, posterPath = "", backdropPath = "", video = false, rating = 0.0, runtime = 0
            )
        )

        val transactionSlot = slot<suspend () -> Unit>()

        coEvery { transactionProvider.runWithTransaction(capture(transactionSlot)) } just Runs
        coEvery { movieDao.deleteByMediaType(any()) } just Runs
        coEvery { movieDao.insertAll(any()) } just Runs

        // Act
        source.deleteByMediaTypeAndInsertAll(mediaType, newMovies)

        // Manually invoke captured transaction block
        transactionSlot.captured.invoke()

        coVerify { movieDao.deleteByMediaType(mediaType) }
        coVerify { movieDao.insertAll(newMovies) }
    }

    @Test
    fun `getRemoteKeyByIdAndMediaType should return correct remote key`() = runTest {
        // Given
        val id = 1
        val mediaType = DatabaseMediaType.Movie.Popular
        val expectedKey = MovieRemoteKeyEntity(
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

    @Test
    fun `handlePaging should delete and insert when shouldDelete is true`() = runTest {
        // Given
        val mediaType = DatabaseMediaType.Movie.Popular
        val movies = listOf<MovieEntity>(mockk())
        val remoteKeys = listOf<MovieRemoteKeyEntity>(mockk())

        val blockSlot = slot<suspend () -> Unit>()
        coEvery { transactionProvider.runWithTransaction(capture(blockSlot)) } just Runs

        coEvery { movieDao.deleteByMediaType(mediaType) } just Runs
        coEvery { remoteKeyDao.deleteByMediaType(mediaType) } just Runs
        coEvery { movieDao.insertAll(movies) } just Runs
        coEvery { remoteKeyDao.insertAll(remoteKeys) } just Runs

        // When
        source.handlePaging(mediaType, movies, remoteKeys, shouldDeleteMoviesAndRemoteKeys = true)
        blockSlot.captured.invoke()

        // Then
        coVerifyOrder {
            movieDao.deleteByMediaType(mediaType)
            remoteKeyDao.deleteByMediaType(mediaType)
            remoteKeyDao.insertAll(remoteKeys)
            movieDao.insertAll(movies)
        }
    }

    @Test
    fun `handlePaging should only insert when shouldDelete is false`() = runTest {
        // Given
        val mediaType = DatabaseMediaType.Movie.Trending
        val movies = listOf<MovieEntity>(mockk())
        val remoteKeys = listOf<MovieRemoteKeyEntity>(mockk())

        val blockSlot = slot<suspend () -> Unit>()
        coEvery { transactionProvider.runWithTransaction(capture(blockSlot)) } just Runs

        coEvery { movieDao.insertAll(movies) } just Runs
        coEvery { remoteKeyDao.insertAll(remoteKeys) } just Runs

        // When
        source.handlePaging(mediaType, movies, remoteKeys, shouldDeleteMoviesAndRemoteKeys = false)
        blockSlot.captured.invoke()

        // Then
        coVerify(exactly = 0) { movieDao.deleteByMediaType(any()) }
        coVerify(exactly = 0) { remoteKeyDao.deleteByMediaType(any()) }
        coVerify { remoteKeyDao.insertAll(remoteKeys) }
        coVerify { movieDao.insertAll(movies) }
    }

}
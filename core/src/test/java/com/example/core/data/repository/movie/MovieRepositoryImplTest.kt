package com.example.core.data.repository.movie

import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.source.MovieDatabaseSource
import com.example.core.data.mapper.asMediaType
import com.example.core.data.mapper.asNetworkMediaType
import com.example.core.data.network.PAGE_SIZE
import com.example.core.data.network.datasource.MovieNetworkDataSource
import com.example.core.data.network.datasource.MultiNetworkDataSource
import com.example.core.data.network.datasource.TvShowNetworkDataSource
import com.example.core.data.network.model.movie.MovieDetailNetwork
import com.example.core.data.network.model.multi.MultiNetwork
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.response.MovieResponse
import com.example.core.data.network.response.MultiResponse
import com.example.core.data.repository.utils.MainDispatcherRule
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.MovieModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import androidx.paging.testing.asSnapshot
import com.example.core.data.network.model.movie.MovieNetwork
import com.example.core.data.network.model.tv.TvShowDetailNetwork
import com.example.core.data.repository.utils.FakeMoviePagingSource
import io.mockk.Runs
import io.mockk.just
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: MovieRepositoryImpl
    private val movieNetworkDataSource: MovieNetworkDataSource = mockk()
    private val databaseDataSource: MovieDatabaseSource = mockk()
    private val tvShowNetworkDataSource: TvShowNetworkDataSource = mockk()
    private val multiNetworkDataSource: MultiNetworkDataSource = mockk()

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(
            databaseDataSource,
            movieNetworkDataSource,
            tvShowNetworkDataSource,
            multiNetworkDataSource
        )
    }

    fun createMockMovieNetworkModel(id: Int): MovieNetwork {
        return MovieNetwork(
            adult = false,
            backdropPath = "/backdrop.jpg",
            genreIds = emptyList(),
            id = id,
            originalLanguage = "en",
            title = "Title $id",
            originalTitle = "Title $id",
            overview = "Overview $id",
            popularity = 7.5,
            posterPath = "/poster.jpg",
            releaseDate = "2023-10-26",
            video = false,
            voteAverage = 8.0,
            voteCount = 100,
        )
    }

    fun createMockMovieEntity(id: Int, mediaType: MediaTypeModel.Movie): MovieEntity { // Changed parameter type
        return MovieEntity(
            id = id,
            title = "Title $id",
            networkId = id,
            overview = "Overview $id",
            adult = false,
            genreIds = emptyList(),
            originalLanguage = "en",
            originalTitle = "Title $id",
            popularity = 7.5,
            posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
            releaseDate = "October 26, 2023",
            voteAverage = 8.0,
            voteCount = 100,
            backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
            runtime = 0,
            video = false,
            rating = 4.0,
            mediaType = mediaType.asMediaType()
        )
    }

    fun createMockDetailMovieModel(id: Int, runtime: Int): MovieDetailNetwork {
        return MovieDetailNetwork(
            id = id,
            adult = false,
            title = "Title $id",
            overview = "Overview $id",
            posterPath = "/poster.jpg",
            releaseDate = "2023-10-26",
            voteAverage = 8.0,
            runtime = runtime,
        )
    }

    fun createMockTvShowNetworkModel(id: Int): TvShowDetailNetwork{
        return TvShowDetailNetwork(
            id = id,
            name = "Tv Show $id",
            overview = "",
            popularity = 0.0,
            firstAirDate = "",
            originalName = "Tv Show $id",
            originalLanguage = "",
            originCountry = emptyList(),
            voteAverage = 0.0,
            voteCount = 0,
            posterPath = "",
            backdropPath = "",
            adult = false,
            numberOfSeasons = 2
        )
    }

    fun createMockMultiNetworkModel(id: Int,mediaType: String): MultiNetwork {
        return MultiNetwork(
            adult = false,
            backdropPath = "/backdrop.jpg",
            genreIds = emptyList(),
            id = id,
            originalLanguage = "en",
            mediaType = mediaType,
            title = "Title $id",
            originalTitle = "Title $id",
            overview = "Overview $id",
            popularity = 7.5,
            posterPath = "/poster.jpg",
            releaseDate = "2023-10-26",
            video = false,
            voteAverage = 8.0,
            voteCount = 100,
        )
    }
    @Test
    fun `searchMovie returns success movie paging data`() = runTest {
        // Arrange
        val query = "title"
        val movie1 = createMockMultiNetworkModel(1,"movie")
        val movie2 = createMockMultiNetworkModel(2,"tv")

        val movieDetail = createMockDetailMovieModel(1,120)
        val tvDetail = createMockTvShowNetworkModel(2)
        val mockResponse = MultiResponse(
            page=1,results = listOf(movie1,movie2),
            totalPages=1, totalResults = 2
        )
        val mockResponse2 = MultiResponse(
            page = 2, results = emptyList(),
            totalPages = 1, totalResults = 2
        )

        coEvery { multiNetworkDataSource.searchMulti(page = 1, query = query)
        } returns CinemaxResponse.Success(mockResponse)
        coEvery { multiNetworkDataSource.searchMulti(page = 2, query = query)
        } returns CinemaxResponse.Success(mockResponse2)
        coEvery { movieNetworkDataSource.getDetailMovie(1)
        } returns CinemaxResponse.Success(movieDetail)
        coEvery { tvShowNetworkDataSource.getDetailTv(2)
        } returns CinemaxResponse.Success(tvDetail)

        // Collect the PagingData and verify its content
        val pagingData = repository.searchMovie(query).asSnapshot()

        assertEquals(2, pagingData.size)
        assertEquals("Title 1", pagingData[0].title)

        coVerify { multiNetworkDataSource.searchMulti(page = 1, query = query) }
        coVerify { multiNetworkDataSource.searchMulti(page = 2, query = query) }
        coVerify { movieNetworkDataSource.getDetailMovie(1) }
        coVerify { tvShowNetworkDataSource.getDetailTv(2) }
    }

    @Test
    fun `searchMovie returns failure movie paging data`() = runTest {
        // Arrange
        val query = "test"
        val errorMessage = "Network error"
        coEvery { multiNetworkDataSource.searchMulti(page = 1, query = query)
        } returns CinemaxResponse.Failure(400,errorMessage)

        // Act
        val exception = assertFailsWith<Exception> {
            repository.searchMovie(query).asSnapshot()
        }

        assertEquals(errorMessage, exception.message)
        coVerify { multiNetworkDataSource.searchMulti(page = 1, query = query) }
    }

    @Test
    fun `searchMovie returns failure IOException movie paging data`() = runTest {
        // Arrange
        val query = "test"
        val errorMessage = "Simulated IO error"
        coEvery { multiNetworkDataSource.searchMulti(page = 1, query = query)
        } throws IOException(errorMessage)

        // Act
        val exception = assertFailsWith<Exception> {
            repository.searchMovie(query).asSnapshot()
        }

        assertEquals(errorMessage, exception.message)
        coVerify { multiNetworkDataSource.searchMulti(page = 1, query = query) }
    }

    @Test
    fun `searchMovie returns failure HTTPException movie paging data`() = runTest {
        // Arrange
        val query = "test"
        val errorCode = 404
        val errorMessage = "Not Found"
        val response = Response.error<MovieResponse>(errorCode, errorMessage.toResponseBody())
        coEvery { multiNetworkDataSource.searchMulti(page = 1, query = query) } throws HttpException(response)

        // Act
        val exception = assertFailsWith<HttpException> {
            repository.searchMovie(query).asSnapshot()
        }

        assertEquals(errorCode, exception.response()?.code())
        coVerify { multiNetworkDataSource.searchMulti(page = 1, query = query) }
    }

    @Test
    fun `getByMediaType returns cached movies data and then fetches and updates`() = runTest {
        val mediaTypeModel = MediaTypeModel.Movie.Popular
        val mediaType = mediaTypeModel.asMediaType()
        val networkType = mediaType.asNetworkMediaType()

        val moviesEntity = listOf(createMockMovieEntity(1,mediaTypeModel), createMockMovieEntity(2,mediaTypeModel))
        val moviesModel = listOf(MovieModel(
            id = 1, title = "Title 1", overview = "Overview 1",
            posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
            releaseDate = "October 26, 2023" , voteAverage = 8.0, runtime = 0, mediaType = "movie",
            originalTitle = "Title 1", originalLanguage = "en", voteCount = 100, profilePath = "",
            popularity = 7.5, rating = 4.0
        ), MovieModel(
            id = 2, title = "Title 2", overview = "Overview 2",
            posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
            releaseDate = "October 26, 2023" , voteAverage = 8.0, runtime = 0, mediaType = "movie",
            originalTitle = "Title 2", originalLanguage = "en", voteCount = 100, profilePath = "",
            popularity = 7.5, rating = 4.0))
        val networkMovies = listOf(createMockMovieNetworkModel(1),createMockMovieNetworkModel(2))
        val movieResponse = MovieResponse(1,networkMovies)

        // Arrange (Cached data exists)
        coEvery { databaseDataSource.getByMediaType(mediaType, PAGE_SIZE) } returns flowOf(emptyList())
        coEvery { movieNetworkDataSource.getByMediaType(networkType) } returns CinemaxResponse.Success(movieResponse)
        coEvery { databaseDataSource.deleteByMediaTypeAndInsertAll(mediaType, any()) } returns Unit
        coEvery { databaseDataSource.getByMediaType(mediaType, PAGE_SIZE) } returns flowOf(moviesEntity)

        // Act
        val results = repository.getByMediaType(mediaTypeModel).toList()

        // Assert (Initial cached data)
        assertTrue(results[0] is CinemaxResponse.Loading)
        assertTrue(results[1] is CinemaxResponse.Loading)
        assertTrue(results[2] is CinemaxResponse.Success)

        val successResult = results[2] as CinemaxResponse.Success
        assertEquals(moviesModel, successResult.value)

        coVerify(atLeast = 1, atMost = 2) { databaseDataSource.getByMediaType(mediaType, PAGE_SIZE) }
        coVerify(exactly = 1) { movieNetworkDataSource.getByMediaType(any()) }
        coVerify { databaseDataSource.deleteByMediaTypeAndInsertAll(mediaType, any()) }
    }

    @Test
    fun `getByMediaType returns network failure error with no cache`() = runTest {
        // Given
        val mediaTypeModel = MediaTypeModel.Movie.Popular
        val mediaType = mediaTypeModel.asMediaType()
        val networkType = mediaType.asNetworkMediaType()
        val errorMessage = "Network Error"

        // Arrange (No cached data, network fetch fails)
        coEvery { databaseDataSource.getByMediaType(mediaType, PAGE_SIZE) } returns flowOf(emptyList())
        coEvery { movieNetworkDataSource.getByMediaType(networkType) } returns CinemaxResponse.Failure(400,errorMessage)

        // Act
        val results = repository.getByMediaType(mediaTypeModel).toList()

        // Assert (Only error)
        assertTrue(results[0] is CinemaxResponse.Loading)
        assertTrue(results[1] is CinemaxResponse.Loading)
        assertTrue(results[2] is CinemaxResponse.Failure)

        val failureResult = results[2] as CinemaxResponse.Failure
        assertEquals(errorMessage, failureResult.error)

        coVerify(atLeast = 1, atMost = 2) { databaseDataSource.getByMediaType(mediaType, PAGE_SIZE) }
        coVerify(exactly = 1) { movieNetworkDataSource.getByMediaType(any()) }
    }

    @Test
    fun `getPagingByMediaType returns success movie paging source data`() = runTest {
        val mediaTypeModel = MediaTypeModel.Movie.Popular
        val mediaType = mediaTypeModel.asMediaType()
        val networkType = mediaType.asNetworkMediaType()
        // Arrange (Database has initial data)
        val moviesEntity = listOf(createMockMovieEntity(1, mediaTypeModel),
            createMockMovieEntity(2, mediaTypeModel))

        val movies = listOf(MovieModel(
            id = 1, title = "Title 1", overview = "Overview 1",
            posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
            releaseDate = "October 26, 2023" , voteAverage = 8.0, runtime = 0, mediaType = "movie",
            originalTitle = "Title 1", originalLanguage = "en", voteCount = 100,
            popularity = 7.5, rating = 4.0, profilePath = ""
        ), MovieModel(
            id = 2, title = "Title 2", overview = "Overview 2",
            posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
            releaseDate = "October 26, 2023" , voteAverage = 8.0, runtime = 0, mediaType = "movie",
            originalTitle = "Title 2", originalLanguage = "en", voteCount = 100,
            popularity = 7.5, rating = 4.0, profilePath = ""
        ))

        val movie1 = createMockMovieNetworkModel(1)
        val movie2 = createMockMovieNetworkModel(2)
        val mockResponse = MovieResponse(1,listOf(movie1, movie2))
        val movieDetail1 = createMockDetailMovieModel(1, 120)
        val movieDetail2 = createMockDetailMovieModel(2, 130)

        coEvery { movieNetworkDataSource.getByMediaType(networkType, 1) } returns CinemaxResponse.Success(mockResponse)
        coEvery { movieNetworkDataSource.getDetailMovie(1) } returns CinemaxResponse.Success(movieDetail1)
        coEvery { movieNetworkDataSource.getDetailMovie(2) } returns CinemaxResponse.Success(movieDetail2)
        coEvery { databaseDataSource.handlePaging(any(), any(), any(), any()) } returns Unit
        coEvery { databaseDataSource.updateMovieRuntime(1, 120) } just Runs
        coEvery { databaseDataSource.updateMovieRuntime(2, 130) } just Runs
        coEvery { databaseDataSource.getPagingByMediaType(mediaType) } returns FakeMoviePagingSource(
            moviesEntity
        )
        coEvery { databaseDataSource.getRemoteKeyByIdAndMediaType(any(), eq(mediaType)) } returns null

        // Act
        val pagingDataFlow = repository.getPagingByMediaType(mediaTypeModel)
        val result = pagingDataFlow.asSnapshot()

        // Assert
        assertEquals(2, result.size)
        assertEquals(movies, result)

        coVerify { movieNetworkDataSource.getByMediaType(networkType, 1) }
        coVerify { movieNetworkDataSource.getDetailMovie(1) }
        coVerify { movieNetworkDataSource.getDetailMovie(2) }
        coVerify { databaseDataSource.updateMovieRuntime(1, 120) }
        coVerify { databaseDataSource.updateMovieRuntime(2, 130) }
        coVerify { databaseDataSource.getPagingByMediaType(mediaType) }
    }

    @Test
    fun `getPagingByMediaType returns network error`() = runTest {
        val mediaTypeModel = MediaTypeModel.Movie.Popular
        val mediaType = mediaTypeModel.asMediaType()
        val networkType = mediaType.asNetworkMediaType()
        val errorMessage = "Network Error"

        coEvery { movieNetworkDataSource.getByMediaType(networkType, 1) } returns CinemaxResponse.Failure(400,errorMessage)
        coEvery { databaseDataSource.getPagingByMediaType(mediaType) } returns FakeMoviePagingSource(
            emptyList()
        )
        coEvery { databaseDataSource.getRemoteKeyByIdAndMediaType(any(), eq(mediaType)) } returns null

        val pagingDataFlow = repository.getPagingByMediaType(mediaTypeModel)

        val exception = assertFailsWith<Exception> {
            pagingDataFlow.asSnapshot()
        }

        assertEquals(errorMessage, exception.message)
    }

}


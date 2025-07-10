package com.example.core.data.network.datasource

import com.example.core.data.network.NetworkMediaType
import com.example.core.data.network.api.MovieApiService
import com.example.core.data.network.model.movie.MovieDetailNetwork
import com.example.core.data.network.model.movie.MovieNetwork
import com.example.core.data.network.model.movie.NetworkListCredits
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.response.MovieResponse
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import retrofit2.Response
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MovieNetworkDataSourceTest {

    private lateinit var apiService: MovieApiService
    private lateinit var dataSource: MovieNetworkDataSource
    private val gson = Gson()

    @Before
    fun setUp() {
        apiService = mockk()
        dataSource = MovieNetworkDataSource(apiService, gson)
    }

    val movieNetwork =  MovieNetwork(id = 1, title = "Mock Movie", overview = "",
        popularity = 0.0, releaseDate = "", adult = false, genreIds = listOf(), originalTitle = "",
        originalLanguage = "en", voteAverage = 0.0, voteCount = 0, posterPath = "", backdropPath = "",
        video = false)

    val listMovies = listOf(movieNetwork)

    val movieDetail = MovieDetailNetwork(
        id = 1, adult = false, backdropPath = "", budget = 0,
        genres = listOf(), homepage = "", imdbId = "tt0241527", originalLanguage = "",
        originalTitle =  "Harry Potter and the Philosopher's Stone", overview = "",
        popularity = 0.0, posterPath = "", releaseDate = "", revenue = 0,
        runtime = 0, status = "", tagline = "",
        title =  "Harry Potter and the Philosopher's Stone",
        video = false, voteAverage = 0.0, voteCount = 0,
        credits = NetworkListCredits(listOf(), listOf()),
    )

    @Test
    fun `getByMediaType should return success when response is 200`() = runTest {
        // Arrange
        val movieResponse = MovieResponse(1, listMovies,1, 1)
        coEvery { apiService.getPopularMovie(any()) } returns Response.success(movieResponse)

        // Act
        val result = dataSource.getByMediaType(NetworkMediaType.Movie.POPULAR)

        // Assert
        assertTrue(result is CinemaxResponse.Success)
        assertEquals(movieResponse, result.value)
    }

    @Test
    fun `getByMediaType should return failure when response is 404`() = runTest {
        val errorJson = """
            {
                "status_code":404,
                "status_message":"Not Found",
                "success":false
            }
        """.trimIndent()

        val errorResponse = errorJson.toResponseBody("application/json".toMediaType())
        coEvery { apiService.getPopularMovie(any()) } returns Response.error(404, errorResponse)

        val result = dataSource.getByMediaType(NetworkMediaType.Movie.POPULAR)

        assertTrue(result is CinemaxResponse.Failure)
        assertEquals(404, result.code)
        assertEquals("Not Found", result.error)
    }

    @Test
    fun `getByMediaType should handle malformed error response`() = runTest {
        val malformed = "<<malformed>>".toResponseBody("application/json".toMediaType())
        coEvery { apiService.getPopularMovie(any()) } returns Response.error(500, malformed)

        val result = dataSource.getByMediaType(NetworkMediaType.Movie.POPULAR)

        assertTrue(result is CinemaxResponse.Failure)
        assertEquals("Malformed error response", result.error)
    }

    @Test
    fun `getByMediaType should return failure on network exception`() = runTest {
        coEvery { apiService.getPopularMovie(any()) } throws RuntimeException("timeout")

        val result = dataSource.getByMediaType(NetworkMediaType.Movie.POPULAR)

        assertTrue(result is CinemaxResponse.Failure)
        assertEquals("timeout", result.error)
    }

    @Test
    fun `getDetailMovie should return success when response is 200`() = runTest {
        coEvery { apiService.getDetailsById(any(), any()) } returns Response.success(movieDetail)

        val result = dataSource.getDetailMovie(1)

        assertTrue(result is CinemaxResponse.Success)
        assertEquals(movieDetail, result.value)
    }

    @Test
    fun `getDetailMovie should return failure on 500`() = runTest {
        val errorJson = """
            {
                "status_code":500,
                "status_message":"Server Error",
                "success":false
            }
        """.trimIndent()

        val responseBody = errorJson.toResponseBody("application/json".toMediaType())
        coEvery { apiService.getDetailsById(any(), any()) } returns Response.error(500, responseBody)

        val result = dataSource.getDetailMovie(1)

        assertTrue(result is CinemaxResponse.Failure)
        assertEquals(500, result.code)
        assertEquals("Server Error", result.error)
    }

}
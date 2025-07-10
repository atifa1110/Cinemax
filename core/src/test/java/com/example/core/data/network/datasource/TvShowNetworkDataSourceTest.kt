package com.example.core.data.network.datasource

import com.example.core.data.network.NetworkMediaType
import com.example.core.data.network.api.TvShowApiService
import com.example.core.data.network.model.movie.NetworkListCredits
import com.example.core.data.network.model.tv.TvShowDetailNetwork
import com.example.core.data.network.model.tv.TvShowNetwork
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.response.TvShowResponse
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TvShowNetworkDataSourceTest {
    private lateinit var apiService: TvShowApiService
    private lateinit var dataSource: TvShowNetworkDataSource
    private val gson = Gson()

    @Before
    fun setUp() {
        apiService = mockk()
        dataSource = TvShowNetworkDataSource(apiService, gson)
    }

    val tvNetwork =  TvShowNetwork(
        id = 1,
        name = "Squid Game",
        overview = "Hundreds of cash-strapped players accept a strange invitation to compete in children's games. Inside, a tempting prize awaits — with deadly high stakes.",
        popularity = 12404.255,
        firstAirDate = "2021-09-17",
        genreIds = listOf( 10759, 9648, 18),
        originalName = "오징어 게임",
        originalLanguage = "ko",
        originCountry = listOf("KR"),
        voteAverage = 7.8,
        voteCount =  14677,
        posterPath = "/dDlEmu3EZ0Pgg93K2SVNLCjCSvE.jpg",
        backdropPath = "/ukAmSyNdtWduHZfm27R2EOsguKt.jpg",
    )

    val listTv = listOf(tvNetwork)

    val tvDetail = TvShowDetailNetwork(
        id = 1,
        name = "Squid Game",
        adult = false,
        backdropPath = "/ukAmSyNdtWduHZfm27R2EOsguKt.jpg",
        createdBy = emptyList(),
        credits = NetworkListCredits(emptyList(), emptyList()),
        episodeRunTime = emptyList(),
        firstAirDate = "2021-09-17",
        genres = emptyList(),
        voteAverage = 7.8,
        voteCount = 14677,
    )

    @Test
    fun `getByMediaType should return success when response is 200`() = runTest {
        // Arrange
        val tvResponse = TvShowResponse(1, listTv,1, 1)
        coEvery { apiService.getPopularTv(any()) } returns Response.success(tvResponse)

        // Act
        val result = dataSource.getByMediaType(NetworkMediaType.TvShow.POPULAR)

        // Assert
        assertTrue(result is CinemaxResponse.Success)
        assertEquals(tvResponse, result.value)
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
        coEvery { apiService.getPopularTv(any()) } returns Response.error(404, errorResponse)

        val result = dataSource.getByMediaType(NetworkMediaType.TvShow.POPULAR)

        assertTrue(result is CinemaxResponse.Failure)
        assertEquals(404, result.code)
        assertEquals("Not Found", result.error)
    }

    @Test
    fun `getByMediaType should handle malformed error response`() = runTest {
        val malformed = "<<malformed>>".toResponseBody("application/json".toMediaType())
        coEvery { apiService.getPopularTv(any()) } returns Response.error(500, malformed)

        val result = dataSource.getByMediaType(NetworkMediaType.TvShow.POPULAR)

        assertTrue(result is CinemaxResponse.Failure)
        assertEquals("Malformed error response", result.error)
    }

    @Test
    fun `getByMediaType should return failure on network exception`() = runTest {
        coEvery { apiService.getPopularTv(any()) } throws RuntimeException("timeout")

        val result = dataSource.getByMediaType(NetworkMediaType.TvShow.POPULAR)

        assertTrue(result is CinemaxResponse.Failure)
        assertEquals("timeout", result.error)
    }

    @Test
    fun `getDetailMovie should return success when response is 200`() = runTest {
        coEvery { apiService.getDetailsById(any(), any()) } returns Response.success(tvDetail)

        val result = dataSource.getDetailTv(1)

        assertTrue(result is CinemaxResponse.Success)
        assertEquals(tvDetail, result.value)
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

        val result = dataSource.getDetailTv(1)

        assertTrue(result is CinemaxResponse.Failure)
        assertEquals(500, result.code)
        assertEquals("Server Error", result.error)
    }

}
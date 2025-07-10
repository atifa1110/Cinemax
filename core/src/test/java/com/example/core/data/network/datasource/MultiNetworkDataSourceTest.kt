package com.example.core.data.network.datasource

import com.example.core.data.network.api.MultiApiService
import com.example.core.data.network.model.multi.MultiNetwork
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.response.MultiResponse
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

class MultiNetworkDataSourceTest {

    private lateinit var apiService: MultiApiService
    private lateinit var dataSource: MultiNetworkDataSource
    private val gson = Gson()

    @Before
    fun setUp() {
        apiService = mockk()
        dataSource = MultiNetworkDataSource(apiService, gson)
    }

    val multiNetwork = MultiNetwork(
        backdropPath = "/5jkE2SzR5uR2egEb1rRhF22JyWN.jpg",
        id = 671,
        title = "Harry Potter and the Philosopher's Stone",
        originalTitle = "Harry Potter and the Philosopher's Stone",
        overview = "",
        posterPath = "/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
        mediaType = "movie",
        adult = false,
        originalLanguage = "en",
        genreIds = listOf(12,14),
        popularity =  234.43,
        releaseDate = "2001-11-16",
        video = false,
        voteAverage = 7.9,
        voteCount = 27593
    )
    val list = listOf(multiNetwork)
    val response = MultiResponse(1, list ,2,30)

    @Test
    fun `getSearchMulti should return success when response is 200`() = runTest {
        coEvery { apiService.getSearchMulti("harry", 1) } returns Response.success(response)

        val result = dataSource.searchMulti("harry")

        assertTrue(result is CinemaxResponse.Success)
        assertEquals(list, result.value.results)
    }

    @Test
    fun `getSearchMulti should return failure on 500`() = runTest {
        val errorJson = """
            {
                "status_code":500,
                "status_message":"Server Error",
                "success":false
            }
        """.trimIndent()

        val responseBody = errorJson.toResponseBody("application/json".toMediaType())
        coEvery { apiService.getSearchMulti("harry", 1) } returns Response.error(500, responseBody)

        val result = dataSource.searchMulti("harry", 1)

        assertTrue(result is CinemaxResponse.Failure)
        assertEquals(500, result.code)
        assertEquals("Server Error", result.error)
    }
}
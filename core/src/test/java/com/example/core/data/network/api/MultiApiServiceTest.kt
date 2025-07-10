package com.example.core.data.network.api

import com.example.core.data.network.model.multi.MultiNetwork
import com.example.core.data.network.response.MultiResponse
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MultiApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: MultiApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        val client = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(MultiApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getSearchMulti should return MultiResponse when response is 200 OK`() = runTest {
        // Load JSON from resources
        val mock = File("src/test/resources/multi/Search.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)


        val actual = apiService.getSearchMulti("harry potter",0).body()
        val expected = MultiResponse(
            1, listOf(MultiNetwork(
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
                voteCount = 27593)
            ),2,30
        )
        assertEquals(expected, actual)

    }

    @Test
    fun `getSearchMulti should return error when API responds with 404`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getSearchMulti("harry potter", 0)

        assertEquals(false, response.isSuccessful)
        assertEquals(404, response.code())
    }

    @Test
    fun `getSearchMulti should return error when API responds with 500`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val response = apiService.getSearchMulti("harry potter", 0)

        assertEquals(false, response.isSuccessful)
        assertEquals(500, response.code())
    }

    @Test
    fun `getSearchMulti should timeout when server is too slow`() = runTest {
        val mockResponse = MockResponse()
            .setBodyDelay(5, java.util.concurrent.TimeUnit.SECONDS)
            .setBody("{}")
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val client = OkHttpClient.Builder()
            .callTimeout(2, java.util.concurrent.TimeUnit.SECONDS) // faster timeout
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val localApiService = retrofit.create(MultiApiService::class.java)

        val result = runCatching {
            localApiService.getSearchMulti("harry potter", 0).body()
        }

        assert(result.isFailure)
    }
}
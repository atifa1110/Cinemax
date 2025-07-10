package com.example.core.data.network.api

import com.example.core.data.network.model.movie.NetworkListCredits
import com.example.core.data.network.model.tv.NetworkEpisode
import com.example.core.data.network.model.tv.TvShowDetailNetwork
import com.example.core.data.network.model.tv.TvShowNetwork
import com.example.core.data.network.response.TvShowResponse
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
class TvShowApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: TvShowApiService

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

        apiService = retrofit.create(TvShowApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getDetailsById should return MultiResponse when response is 200 OK`() = runTest{
        // Load JSON from resources
        val mock = File("src/test/resources/tvs/Details.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        val actual = apiService.getDetailsById(93405).body()
        val expected = TvShowDetailNetwork(
            id = 93405,
            name = "Squid Game",
            adult = false,
            backdropPath = "/ukAmSyNdtWduHZfm27R2EOsguKt.jpg",
            createdBy = emptyList(),
            credits = NetworkListCredits(emptyList(), emptyList()),
            episodeRunTime = emptyList(),
            firstAirDate = "2021-09-17",
            genres = emptyList(),
            homepage = "https://www.netflix.com/title/81040344",
            inProduction= true,
            languages = emptyList(),
            lastAirDate= "2024-12-26",
            lastEpisodeToAir = NetworkEpisode(
                5747919, "Friend or Foe","2024-12-26",7,
                "The remaining players strategize on how to survive the night. Gi-hun proposes a risky plan — but he will need trustworthy allies to carry it out.",
                "",61,2, 93405,"/tO8hO3jWp4JveXmBbPikd4IQbss.jpg",8.302,43
            ),
            organizations = emptyList(),
            nextEpisodeToAir = null,
            numberOfEpisodes = 16,
            numberOfSeasons = 3,
            originCountry = emptyList(),
            originalLanguage = "ko",
            originalName = "오징어 게임",
            overview = "Hundreds of cash-strapped players accept a strange invitation to compete in children's games. Inside, a tempting prize awaits — with deadly high stakes.",
            popularity = 12404.255,
            posterPath = "/dDlEmu3EZ0Pgg93K2SVNLCjCSvE.jpg",
            productionCompanies = emptyList(),
            productionCountries =  emptyList(),
            seasons = emptyList(),
            spokenLanguages = emptyList(),
            status = "Returning Series",
            tagline = "45.6 billion won is child's play.",
            type = "Scripted",
            voteAverage = 7.8,
            voteCount = 14677,
        )
        assertEquals(expected, actual)

    }

    @Test
    fun `getOnTheAir should return MultiResponse when response is 200 OK`() = runTest {
        // Load JSON from resources
        val mock = File("src/test/resources/tvs/OnTheAir.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val actual = apiService.getOnTheAirTv().body()
        val expected = TvShowResponse(
            1, listOf(
                TvShowNetwork(
                    id = 93405,
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
            ),2,30
        )
        assertEquals(expected, actual)

    }

    @Test
    fun `getPopularTv should return MultiResponse when response is 200 OK`() = runTest {
        // Load JSON from resources
        val mock = File("src/test/resources/tvs/Popular.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val actual = apiService.getPopularTv().body()
        val expected = TvShowResponse(
            1, listOf(
                TvShowNetwork(
                    id = 93405,
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
            ),2,30
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `getTopRatedTv should return MultiResponse when response is 200 OK`() = runTest {
        // Load JSON from resources
        val mock = File("src/test/resources/tvs/TopRated.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)


        val actual = apiService.getTopRatedTv().body()
        val expected = TvShowResponse(
            1, listOf(
                TvShowNetwork(
                    id = 93405,
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
            ),2,30
        )
        assertEquals(expected, actual)

    }

    @Test
    fun `getDiscoverTv should return MultiResponse when response is 200 OK`() = runTest {
        // Load JSON from resources
        val mock = File("src/test/resources/tvs/Discover.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)


        val actual = apiService.getDiscoverTv().body()
        val expected = TvShowResponse(
            1, listOf(
                TvShowNetwork(
                    id = 93405,
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
            ),2,30
        )
        assertEquals(expected, actual)

    }
}
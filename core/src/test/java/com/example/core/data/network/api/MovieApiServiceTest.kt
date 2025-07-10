package com.example.core.data.network.api

import com.example.core.data.network.model.genre.GenreNetwork
import com.example.core.data.network.model.movie.MovieDetailNetwork
import com.example.core.data.network.model.movie.MovieNetwork
import com.example.core.data.network.model.movie.NetworkBelongsToCollection
import com.example.core.data.network.model.movie.NetworkListCredits
import com.example.core.data.network.model.movie.NetworkListImages
import com.example.core.data.network.model.movie.NetworkListVideos
import com.example.core.data.network.model.movie.NetworkProductionCompany
import com.example.core.data.network.model.movie.NetworkSpokenLanguage
import com.example.core.data.network.response.MovieResponse
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
class MovieApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: MovieApiService

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

        apiService = retrofit.create(MovieApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getDetailsById should return MultiResponse when response is 200 OK`() = runTest{
        // Load JSON from resources
        val mock = File("src/test/resources/movies/Details.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val actual = apiService.getDetailsById(671).body()
        val expected = MovieDetailNetwork(
            id = 671,
            adult = false,
            backdropPath = "/5jkE2SzR5uR2egEb1rRhF22JyWN.jpg",
            belongsToCollection = NetworkBelongsToCollection(
                id = 1241,
                name = "Harry Potter Collection",
                posterPath = "/s4hXqX1VyWMc2ctJRuNBDB7YNJ3.jpg",
                backdropPath = "/xN6SBJVG8jqqKQrgxthn3J2m49S.jpg"
            ),
            budget = 125000000,
            genres = listOf(
                GenreNetwork(id = 12, name = "Adventure"),
                GenreNetwork(id = 14, name = "Fantasy")
            ),
            homepage = "https://www.warnerbros.com/movies/harry-potter-and-sorcerers-stone/",
            imdbId = "tt0241527",
            originalLanguage = "en",
            originalTitle = "Harry Potter and the Philosopher's Stone",
            overview = "Harry Potter has lived under the stairs at his aunt and uncle's house his whole life. But on his 11th birthday, he learns he's a powerful wizard—with a place waiting for him at the Hogwarts School of Witchcraft and Wizardry. As he learns to harness his newfound powers with the help of the school's kindly headmaster, Harry uncovers the truth about his parents' deaths—and about the villain who's to blame.",
            popularity = 239.401,
            posterPath = "/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
            productionCompanies = listOf(
                NetworkProductionCompany(
                    id = 174,
                    logoPath = "/zhD3hhtKB5qyv7ZeL4uLpNxgMVU.png",
                    name = "Warner Bros. Pictures",
                    originCountry = "US"
                ),
            ),
            productionCountries = emptyList(),
            releaseDate = "2001-11-16",
            revenue = 976475550L,
            runtime = 152,
            spokenLanguages = listOf(
                NetworkSpokenLanguage(
                    englishName = "English",
                    iso = "en",
                    name = "English"
                )
            ),
            status = "Released",
            tagline = "Let the magic begin.",
            title = "Harry Potter and the Philosopher's Stone",
            video = false,
            voteAverage = 7.907,
            voteCount = 27620,
            credits = NetworkListCredits(emptyList(), emptyList()),
            images = NetworkListImages(emptyList(), emptyList()),
            videos = NetworkListVideos(emptyList())
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `getNowPlayingMovie should return MultiResponse when response is 200 OK`() = runTest{
        // Load JSON from resources
        val mock = File("src/test/resources/movies/NowPlaying.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val actual = apiService.getNowPlayingMovie().body()
        val expected = MovieResponse(
            1, listOf(MovieNetwork(
                backdropPath = "/5jkE2SzR5uR2egEb1rRhF22JyWN.jpg",
                id = 671,
                title = "Harry Potter and the Philosopher's Stone",
                originalTitle = "Harry Potter and the Philosopher's Stone",
                overview = "",
                posterPath = "/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
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
    fun `getPopularMovie should return MultiResponse when response is 200 OK`() = runTest {
        // Load JSON from resources
        val mock = File("src/test/resources/movies/Popular.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)


        val actual = apiService.getPopularMovie().body()
        val expected = MovieResponse(
            1, listOf(MovieNetwork(
                backdropPath = "/5jkE2SzR5uR2egEb1rRhF22JyWN.jpg",
                id = 671,
                title = "Harry Potter and the Philosopher's Stone",
                originalTitle = "Harry Potter and the Philosopher's Stone",
                overview = "",
                posterPath = "/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
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
    fun `getTopRatedMovie should return MultiResponse when response is 200 OK`() =  runTest {
        // Load JSON from resources
        val mock = File("src/test/resources/movies/TopRated.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)


        val actual = apiService.getTopRatedMovie().body()
        val expected = MovieResponse(
            1, listOf(MovieNetwork(
                backdropPath = "/5jkE2SzR5uR2egEb1rRhF22JyWN.jpg",
                id = 671,
                title = "Harry Potter and the Philosopher's Stone",
                originalTitle = "Harry Potter and the Philosopher's Stone",
                overview = "",
                posterPath = "/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
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
    fun `getTrendingMovie should return MultiResponse when response is 200 OK`() = runTest {
        // Load JSON from resources
        val mock = File("src/test/resources/movies/Trending.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val actual = apiService.getTrendingMovie().body()
        val expected = MovieResponse(
            1, listOf(MovieNetwork(
                backdropPath = "/5jkE2SzR5uR2egEb1rRhF22JyWN.jpg",
                id = 671,
                title = "Harry Potter and the Philosopher's Stone",
                originalTitle = "Harry Potter and the Philosopher's Stone",
                overview = "",
                posterPath = "/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
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
    fun `getUpcomingMovie should return MultiResponse when response is 200 OK`() = runTest {
        // Load JSON from resources
        val mock = File("src/test/resources/movies/Upcoming.json").readText()
        println("Loaded JSON: $mock")
        val mockResponse = MockResponse()
            .setBody(mock)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val actual = apiService.getUpcomingMovie().body()
        val expected = MovieResponse(
            1, listOf(MovieNetwork(
                backdropPath = "/5jkE2SzR5uR2egEb1rRhF22JyWN.jpg",
                id = 671,
                title = "Harry Potter and the Philosopher's Stone",
                originalTitle = "Harry Potter and the Philosopher's Stone",
                overview = "",
                posterPath = "/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
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
}
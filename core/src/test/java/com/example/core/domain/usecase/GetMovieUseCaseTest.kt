package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.MovieModel
import com.example.core.domain.repository.movie.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieUseCaseTest {

    private val repository: MovieRepository = mockk()
    private lateinit var useCase: GetMovieUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = GetMovieUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit success with movie list when repository returns success`() = runTest {
        // Given
        val dummyMovies = listOf(
            MovieModel(
                id = 1,
                title = "Dummy Movie",
                overview = "Some overview",
                popularity = 10.0,
                releaseDate = "2025-01-01",
                adult = false,
                genres = emptyList(),
                originalTitle = "Dummy Movie",
                originalLanguage = "en",
                voteAverage = 7.5,
                voteCount = 100,
                posterPath = "",
                backdropPath = "",
                video = false,
                rating = 3.5,
                mediaType = "movie",
                profilePath = "",
                runtime = 120
            )
        )

        val mediaType = MediaTypeModel.Movie.Popular
        val expected = CinemaxResponse.Success(dummyMovies)

        coEvery { repository.getByMediaType(mediaType) } returns flowOf(expected)

        // When
        val result = useCase.invoke(mediaType).first()

        // Then
        assertTrue(result is CinemaxResponse.Success)
        assertEquals(dummyMovies, result.value)
    }

    @Test
    fun `should emit error when repository returns error`() = runTest {
        // Given
        val mediaType = MediaTypeModel.Movie.Popular
        val error = CinemaxResponse.Failure(404,"Failed to fetch data")

        coEvery { repository.getByMediaType(mediaType) } returns flowOf(error)

        // When
        val result = useCase(mediaType).first()

        // Then
        assertTrue(result is CinemaxResponse.Failure)
        assertEquals("Failed to fetch data", result.error)
    }
}


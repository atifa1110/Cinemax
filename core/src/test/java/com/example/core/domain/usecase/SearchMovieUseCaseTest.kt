package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.domain.model.MovieModel
import com.example.core.domain.repository.movie.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchMovieUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: SearchMovieUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = SearchMovieUseCase(repository)
    }

    @Test
    fun `invoke should return flow of PagingData of MovieModel`() = runTest {
        // Given
        val query = "Oppenheimer"
        val expectedMovie = MovieModel(
            id = 1,
            title = "Oppenheimer",
            overview = "Some overview",
            popularity = 99.0,
            releaseDate = "2023-07-21",
            adult = false,
            genres = emptyList(),
            originalTitle = "Oppenheimer",
            originalLanguage = "en",
            voteAverage = 9.0,
            voteCount = 1000,
            posterPath = "/somepath.jpg",
            backdropPath = "/somebackdrop.jpg",
            video = false,
            rating = 4.5,
            mediaType = "movie",
            profilePath = "",
            runtime = 180
        )
        val expected = PagingData.from(listOf(expectedMovie))

        coEvery { repository.searchMovie(query) } returns flowOf(expected)

        // When
        val result = useCase(query)

        // Then
        result.collect { pagingData ->
            // Because PagingData is a stream-based data structure,
            // we can't directly assert it; instead we check if it's emitted
            assertEquals(expected, pagingData)
        }
    }
}

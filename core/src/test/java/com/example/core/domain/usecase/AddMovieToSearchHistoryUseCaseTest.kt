package com.example.core.domain.usecase

import com.example.core.domain.model.SearchModel
import com.example.core.domain.repository.search.SearchHistoryRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddMovieToSearchHistoryUseCaseTest {

    private lateinit var repository: SearchHistoryRepository
    private lateinit var useCase: AddMovieToSearchHistoryUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = AddMovieToSearchHistoryUseCase(repository)
    }

    @Test
    fun `invoke calls addMovieToSearchHistory on repository`() = runTest {
        val searchModel = SearchModel(
            id = 1,
            title = "Inception",
            overview = "Dream within a dream",
            popularity = 8.7,
            releaseDate = "2010-07-16",
            adult = false,
            genres = listOf(),
            originalTitle = "Inception",
            originalLanguage = "en",
            voteAverage = 8.8,
            voteCount = 10000,
            profilePath = "",
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            video = false,
            rating = 4.5,
            mediaType = "movie",
            runtime = 148,
            timestamp = 1234567890L
        )

        useCase.invoke(searchModel)

        coVerify(exactly = 1) { repository.addMovieToSearchHistory(searchModel) }
    }
}

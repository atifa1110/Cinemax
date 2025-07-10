package com.example.core.domain.usecase

import com.example.core.domain.repository.search.SearchHistoryRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteMovieFromSearchHistoryUseCaseTest {

    private lateinit var repository: SearchHistoryRepository
    private lateinit var useCase: DeleteMovieFromSearchHistoryUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = DeleteMovieFromSearchHistoryUseCase(repository)
    }

    @Test
    fun `invoke should call removeMovieFromSearchHistory on repository with correct id`() = runTest {
        val movieId = 123

        useCase(movieId)

        coVerify(exactly = 1) {
            repository.removeMovieFromSearchHistory(movieId)
        }
    }
}

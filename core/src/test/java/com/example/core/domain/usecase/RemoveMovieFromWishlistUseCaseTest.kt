package com.example.core.domain.usecase

import com.example.core.domain.repository.wishlist.WishListRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoveMovieFromWishlistUseCaseTest {

    private lateinit var repository: WishListRepository
    private lateinit var useCase: RemoveMovieFromWishlistUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = RemoveMovieFromWishlistUseCase(repository)
    }

    @Test
    fun `invoke should call removeMovieFromWishlist with correct id`() = runTest {
        // Given
        val movieId = 123
        coEvery { repository.removeMovieFromWishlist(movieId) } returns Unit

        // When
        useCase(movieId)

        // Then
        coVerify(exactly = 1) { repository.removeMovieFromWishlist(movieId) }
    }
}

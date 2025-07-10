package com.example.core.domain.usecase

import com.example.core.domain.repository.wishlist.WishListRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoveTvShowFromWishlistUseCaseTest {

    private lateinit var repository: WishListRepository
    private lateinit var useCase: RemoveTvShowFromWishlistUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = RemoveTvShowFromWishlistUseCase(repository)
    }

    @Test
    fun `invoke should call removeTvFromWishlist with correct id`() = runTest {
        // Given
        val movieId = 123
        coEvery { repository.removeTvShowFromWishlist(movieId) } returns Unit

        // When
        useCase(movieId)

        // Then
        coVerify(exactly = 1) { repository.removeTvShowFromWishlist(movieId) }
    }
}

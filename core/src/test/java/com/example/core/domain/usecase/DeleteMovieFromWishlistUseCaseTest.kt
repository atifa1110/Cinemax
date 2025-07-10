package com.example.core.domain.usecase

import com.example.core.domain.repository.wishlist.WishListRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteMovieFromWishlistUseCaseTest {

    private lateinit var repository: WishListRepository
    private lateinit var useCase: DeleteMovieFromWishlistUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = DeleteMovieFromWishlistUseCase(repository)
    }

    @Test
    fun `invoke should call removeMovieFromSearchHistory on repository with correct id`() = runTest {
        val movieId = 123

        useCase(movieId)

        coVerify(exactly = 1) {
            repository.removeMovieFromWishlist(movieId)
        }
    }
}

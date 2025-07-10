package com.example.core.domain.usecase
import com.example.core.domain.repository.wishlist.WishListRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteTvShowFromWishlistUseCaseTest {

    private val repository = mockk<WishListRepository>(relaxed = true)
    private val useCase = DeleteTvShowFromWishlistUseCase(repository)

    @Test
    fun `invoke should call repository with correct id`() = runTest {
        // Given
        val tvShowId = 42

        // When
        useCase(tvShowId)

        // Then
        coVerify { repository.removeTvShowFromWishlist(tvShowId) }
    }
}

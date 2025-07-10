package com.example.core.domain.usecase

import app.cash.turbine.test
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.WishlistModel
import com.example.core.domain.repository.wishlist.WishListRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetWishlistUseCaseTest {

    private lateinit var wishlistRepository: WishListRepository
    private lateinit var getWishlistUseCase: GetWishlistUseCase

    @Before
    fun setup() {
        wishlistRepository = mockk()
        getWishlistUseCase = GetWishlistUseCase(wishlistRepository)
    }

    @Test
    fun `invoke returns success with wishlist`() = runTest {
        // Given
        val mockWishlist = listOf(
            WishlistModel(id = 1, mediaType = MediaTypeModel.Wishlist.Movie, title = "Inception",
                genre = emptyList(), rating = 2.0, posterPath ="",isWishListed = false
            ),
            WishlistModel(id = 2, mediaType = MediaTypeModel.Wishlist.TvShow, title = "Inception2",
                genre = emptyList(), rating = 2.0, posterPath ="",isWishListed = false
            )
        )
        val mockResponse = CinemaxResponse.Success(mockWishlist)

        coEvery { wishlistRepository.getWishlist() } returns flowOf(mockResponse)

        // When + Then
        getWishlistUseCase().test {
            val item = awaitItem()
            assertEquals(mockResponse, item)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns error`() = runTest {
        // Given
        val mockError = CinemaxResponse.Failure(400,"Something went wrong")
        coEvery { wishlistRepository.getWishlist() } returns flowOf(mockError)

        // When + Then
        getWishlistUseCase().test {
            val item = awaitItem()
            assertEquals(mockError, item)
            awaitComplete()
        }
    }
}

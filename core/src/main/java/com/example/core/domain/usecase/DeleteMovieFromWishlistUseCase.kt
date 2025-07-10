package com.example.core.domain.usecase

import com.example.core.domain.repository.wishlist.WishListRepository
import javax.inject.Inject

class DeleteMovieFromWishlistUseCase @Inject constructor(
    private val repository: WishListRepository
) {
    suspend operator fun invoke(id: Int) =
        repository.removeMovieFromWishlist(id)
}
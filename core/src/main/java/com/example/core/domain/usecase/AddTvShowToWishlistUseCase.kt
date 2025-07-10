package com.example.core.domain.usecase

import com.example.core.domain.model.TvShowDetailModel
import com.example.core.domain.repository.wishlist.WishListRepository
import javax.inject.Inject

class AddTvShowToWishlistUseCase @Inject constructor(
    private val repository: WishListRepository
) {
    suspend operator fun invoke(tvShow : TvShowDetailModel) = repository.addTvShowToWishlist(tvShow)
}
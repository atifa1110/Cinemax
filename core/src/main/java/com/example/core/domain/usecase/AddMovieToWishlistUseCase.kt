package com.example.core.domain.usecase

import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.repository.wishlist.WishListRepository
import javax.inject.Inject

class AddMovieToWishlistUseCase @Inject constructor(
    private val repository: WishListRepository
) {
    suspend operator fun invoke(movie : MovieDetailModel) = repository.addMovieToWishlist(movie)
}
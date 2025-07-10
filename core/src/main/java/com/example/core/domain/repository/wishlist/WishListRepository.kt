package com.example.core.domain.repository.wishlist

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.model.TvShowDetailModel
import com.example.core.domain.model.WishlistModel
import kotlinx.coroutines.flow.Flow

interface WishListRepository {
    fun getWishlist(): Flow<CinemaxResponse<List<WishlistModel>>>

    suspend fun addMovieToWishlist(movie : MovieDetailModel)
    suspend fun addTvShowToWishlist(tvShow : TvShowDetailModel)

    suspend fun removeMovieFromWishlist(id: Int)
    suspend fun removeTvShowFromWishlist(id: Int)

}
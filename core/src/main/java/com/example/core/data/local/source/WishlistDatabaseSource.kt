package com.example.core.data.local.source

import com.example.core.data.local.dao.wishlist.WishlistDao
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.mapper.asWishlistEntity
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.model.TvShowDetailModel
import javax.inject.Inject

class WishlistDatabaseSource @Inject constructor(
    private val wishlistDao : WishlistDao
) {
    fun getWishlist() = wishlistDao.getByMediaType()

    suspend fun addMovieToWishlist(movie: MovieDetailModel) =
        wishlistDao.insert(DatabaseMediaType.Wishlist.Movie.asWishlistEntity(movie))

    suspend fun addTvShowToWishlist(tvShow : TvShowDetailModel) =
        wishlistDao.insert(DatabaseMediaType.Wishlist.TvShow.asWishlistEntity(tvShow))

    suspend fun removeMovieFromWishlist(id: Int) =
        wishlistDao.deleteByMediaTypeAndNetworkId(DatabaseMediaType.Wishlist.Movie, id)

    suspend fun removeTvShowFromWishlist(id: Int) =
        wishlistDao.deleteByMediaTypeAndNetworkId(DatabaseMediaType.Wishlist.TvShow, id)

    suspend fun isMovieWishListed(id: Int) = wishlistDao.isWishListed(DatabaseMediaType.Wishlist.Movie, id)
    suspend fun isTvShowWishListed(id: Int) = wishlistDao.isWishListed(DatabaseMediaType.Wishlist.TvShow, id)
}
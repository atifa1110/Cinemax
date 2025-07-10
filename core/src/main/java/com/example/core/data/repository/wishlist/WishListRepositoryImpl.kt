package com.example.core.data.repository.wishlist

import com.example.core.data.local.model.wishlist.WishlistEntity
import com.example.core.data.local.source.WishlistDatabaseSource
import com.example.core.data.mapper.asWishlistModel
import com.example.core.data.mapper.listMap
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.model.TvShowDetailModel
import com.example.core.domain.model.WishlistModel
import com.example.core.domain.repository.wishlist.WishListRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WishListRepositoryImpl @Inject constructor(
    private val databaseSource: WishlistDatabaseSource
): WishListRepository {

    override fun getWishlist(): Flow<CinemaxResponse<List<WishlistModel>>> {
        return flow {
            try {
                emit(CinemaxResponse.Loading)
                delay(1000L)
                val result = databaseSource.getWishlist().listMap(WishlistEntity::asWishlistModel).first()
                emit(CinemaxResponse.Success(result))
            } catch (e: Exception) {
                emit(CinemaxResponse.Failure(-1,e.localizedMessage ?: "Unexpected Error"))
            }
        }
    }


    override suspend fun addMovieToWishlist(movie: MovieDetailModel) {
        databaseSource.addMovieToWishlist(movie)
    }

    override suspend fun addTvShowToWishlist(tvShow: TvShowDetailModel) {
        databaseSource.addTvShowToWishlist(tvShow)
    }

    override suspend fun removeMovieFromWishlist(id: Int) {
        databaseSource.removeMovieFromWishlist(id)
    }

    override suspend fun removeTvShowFromWishlist(id: Int) {
        databaseSource.removeTvShowFromWishlist(id)
    }
}
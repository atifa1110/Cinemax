package com.example.core.data.repository.moviedetail

import com.example.core.data.local.source.MovieDetailsDatabaseSource
import com.example.core.data.local.source.WishlistDatabaseSource
import com.example.core.data.mapper.asMovieDetailsEntity
import com.example.core.data.mapper.asMovieDetailsModel
import com.example.core.data.network.datasource.MovieNetworkDataSource
import com.example.core.data.network.networkBoundResource
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.repository.moviedetail.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val databaseDataSource: MovieDetailsDatabaseSource,
    private val networkDataSource: MovieNetworkDataSource,
    private val wishlistDatabaseSource: WishlistDatabaseSource
) : MovieDetailRepository {

    override fun getDetailMovie(id: Int): Flow<CinemaxResponse<MovieDetailModel?>> {
       return networkBoundResource(
           query = { databaseDataSource.getById(id).map { it?.asMovieDetailsModel(isWishListed = wishlistDatabaseSource.isMovieWishListed(id)) } },
           fetch = { networkDataSource.getDetailMovie(id) },
           saveFetchResult = { response ->
                databaseDataSource.deleteAndInsert(response.asMovieDetailsEntity())
           }
       )
    }
}
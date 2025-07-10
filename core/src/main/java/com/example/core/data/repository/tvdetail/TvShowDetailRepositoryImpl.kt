package com.example.core.data.repository.tvdetail

import com.example.core.data.local.source.TvShowDetailsDatabaseSource
import com.example.core.data.local.source.WishlistDatabaseSource
import com.example.core.data.mapper.asTvShowDetailsEntity
import com.example.core.data.mapper.asTvShowDetailsModel
import com.example.core.data.network.datasource.TvShowNetworkDataSource
import com.example.core.data.network.networkBoundResource
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.TvShowDetailModel
import com.example.core.domain.repository.tvdetail.TvShowDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvShowDetailRepositoryImpl @Inject constructor(
    private val databaseDataSource: TvShowDetailsDatabaseSource,
    private val networkDataSource: TvShowNetworkDataSource,
    private val wishlistDatabaseSource: WishlistDatabaseSource
) : TvShowDetailRepository {

    override fun getDetailTvShow(id: Int): Flow<CinemaxResponse<TvShowDetailModel?>> {
        return networkBoundResource(
            query = { databaseDataSource.getById(id).map { it?.asTvShowDetailsModel(isWishListed = wishlistDatabaseSource.isTvShowWishListed(id)) } },
            fetch = { networkDataSource.getDetailTv(id) },
            saveFetchResult = { response ->
                databaseDataSource.deleteAndInsert(response.asTvShowDetailsEntity())
            }
        )
    }

}
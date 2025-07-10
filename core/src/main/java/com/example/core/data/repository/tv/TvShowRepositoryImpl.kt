package com.example.core.data.repository.tv

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.core.data.local.model.tv.TvShowEntity
import com.example.core.data.local.source.TvShowDatabaseSource
import com.example.core.data.local.source.TvShowRemoteMediator
import com.example.core.data.mapper.asMediaType
import com.example.core.data.mapper.asNetworkMediaType
import com.example.core.data.mapper.asTvShowEntity
import com.example.core.data.mapper.asTvShowModel
import com.example.core.data.mapper.listMap
import com.example.core.data.mapper.pagingMap
import com.example.core.data.network.PAGE_SIZE
import com.example.core.data.network.datasource.TvShowNetworkDataSource
import com.example.core.data.network.defaultPagingConfig
import com.example.core.data.network.networkBoundResource
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.TvShowModel
import com.example.core.domain.repository.tv.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.collections.map

class TvShowRepositoryImpl @Inject constructor(
    private val databaseDataSource: TvShowDatabaseSource,
    private val networkDataSource: TvShowNetworkDataSource
) : TvShowRepository {

    override fun getByMediaType(mediaTypeModel: MediaTypeModel.TvShow): Flow<CinemaxResponse<List<TvShowModel>>> {
        val mediaType = mediaTypeModel.asMediaType()
        return networkBoundResource(
            query = {  databaseDataSource.getByMediaType(
                mediaType = mediaType, pageSize = PAGE_SIZE
            ).listMap(TvShowEntity::asTvShowModel)
            },
            fetch = { networkDataSource.getByMediaType(mediaType.asNetworkMediaType()) },
            saveFetchResult = { response ->
                val result = response.results?: emptyList()
                databaseDataSource.deleteByMediaTypeAndInsertAll(
                    mediaType = mediaType,
                    tvShows = result.map { it.asTvShowEntity(mediaType) }
                )
            },
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingByMediaType(mediaTypeModel: MediaTypeModel.TvShow): Flow<PagingData<TvShowModel>> {
        val mediaType = mediaTypeModel.asMediaType()
        return Pager(
            config = defaultPagingConfig,
            remoteMediator = TvShowRemoteMediator(databaseDataSource, networkDataSource, mediaType),
            pagingSourceFactory = { databaseDataSource.getPagingByMediaType(mediaType) }
        ).flow.pagingMap(TvShowEntity::asTvShowModel)
    }

}
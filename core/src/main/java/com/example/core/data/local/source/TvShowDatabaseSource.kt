package com.example.core.data.local.source

import androidx.paging.PagingSource
import com.example.core.data.local.dao.tv.TvShowDao
import com.example.core.data.local.dao.tv.TvShowRemoteKeyDao
import com.example.core.data.local.model.tv.TvShowEntity
import com.example.core.data.local.model.tv.TvShowRemoteKeyEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.local.util.DatabaseTransactionProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowDatabaseSource @Inject constructor(
    private val tvShowDao: TvShowDao,
    private val tvShowRemoteKeyDao: TvShowRemoteKeyDao,
    private val transactionProvider: DatabaseTransactionProvider
) {
    suspend fun updateTvSeasons(id: Int, seasons: Int) = tvShowDao.updateSeasons(id, seasons)

    fun getByMediaType(mediaType: DatabaseMediaType.TvShow, pageSize: Int): Flow<List<TvShowEntity>> =
        tvShowDao.getByMediaType(mediaType, pageSize)

    fun getPagingByMediaType(mediaType:DatabaseMediaType.TvShow): PagingSource<Int, TvShowEntity> =
        tvShowDao.getPagingByMediaType(mediaType)

    suspend fun insertAll(tvShows: List<TvShowEntity>) = tvShowDao.insertAll(tvShows)

    suspend fun deleteByMediaTypeAndInsertAll(
        mediaType: DatabaseMediaType.TvShow,
        tvShows: List<TvShowEntity>
    ) = transactionProvider.runWithTransaction {
        tvShowDao.deleteByMediaType(mediaType)
        tvShowDao.insertAll(tvShows)
    }

    suspend fun getRemoteKeyByIdAndMediaType(id: Int, mediaType: DatabaseMediaType.TvShow) =
        tvShowRemoteKeyDao.getByIdAndMediaType(id, mediaType)

    suspend fun handlePaging(
        mediaType: DatabaseMediaType.TvShow,
        tvs: List<TvShowEntity>,
        remoteKeys: List<TvShowRemoteKeyEntity>,
        shouldDeleteMoviesAndRemoteKeys: Boolean
    ) = transactionProvider.runWithTransaction {
        if (shouldDeleteMoviesAndRemoteKeys) {
            tvShowDao.deleteByMediaType(mediaType)
            tvShowRemoteKeyDao.deleteByMediaType(mediaType)
        }
        tvShowRemoteKeyDao.insertAll(remoteKeys)
        tvShowDao.insertAll(tvs)
    }

}
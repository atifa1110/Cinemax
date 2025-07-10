package com.example.core.data.local.source

import androidx.paging.PagingSource
import com.example.core.data.local.dao.movie.MovieDao
import com.example.core.data.local.dao.movie.MovieRemoteKeyDao
import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.model.movie.MovieRemoteKeyEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.local.util.DatabaseTransactionProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDatabaseSource @Inject constructor(
    private val movieDao: MovieDao,
    private val movieRemoteKeyDao: MovieRemoteKeyDao,
    private val transactionProvider: DatabaseTransactionProvider
) {

    suspend fun updateMovieRuntime(id: Int, runtime: Int) = movieDao.updateRuntime(id, runtime)

    fun getByMediaType(mediaType: DatabaseMediaType.Movie, pageSize: Int): Flow<List<MovieEntity>> =
        movieDao.getByMediaType(mediaType, pageSize)

    fun getPagingByMediaType(mediaType: DatabaseMediaType.Movie): PagingSource<Int, MovieEntity> =
        movieDao.getPagingByMediaType(mediaType)

    suspend fun deleteByMediaTypeAndInsertAll(
        mediaType: DatabaseMediaType.Movie,
        movies: List<MovieEntity>
    ) = transactionProvider.runWithTransaction {
        movieDao.deleteByMediaType(mediaType)
        movieDao.insertAll(movies)
    }

    suspend fun getRemoteKeyByIdAndMediaType(id: Int, mediaType: DatabaseMediaType.Movie) =
        movieRemoteKeyDao.getByIdAndMediaType(id, mediaType)

    suspend fun handlePaging(
        mediaType: DatabaseMediaType.Movie,
        movies: List<MovieEntity>,
        remoteKeys: List<MovieRemoteKeyEntity>,
        shouldDeleteMoviesAndRemoteKeys: Boolean
    ) = transactionProvider.runWithTransaction {
        if (shouldDeleteMoviesAndRemoteKeys) {
            movieDao.deleteByMediaType(mediaType)
            movieRemoteKeyDao.deleteByMediaType(mediaType)
        }
        movieRemoteKeyDao.insertAll(remoteKeys)
        movieDao.insertAll(movies)
    }

}
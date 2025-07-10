package com.example.core.data.local.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.model.movie.MovieRemoteKeyEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.local.util.PagingUtils
import com.example.core.data.mapper.asMovieEntity
import com.example.core.data.mapper.asNetworkMediaType
import com.example.core.data.network.datasource.MovieNetworkDataSource
import com.example.core.data.network.response.CinemaxResponse
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val databaseDataSource: MovieDatabaseSource,
    private val networkDataSource: MovieNetworkDataSource,
    private val mediaType: DatabaseMediaType.Movie
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKey?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    nextPage
                }
            }

            val response = networkDataSource.getByMediaType(
                mediaType = mediaType.asNetworkMediaType(),
                page = currentPage
            )

            when(response) {
                is CinemaxResponse.Success -> {
                    val movies = response.value.results?.map { it.asMovieEntity(mediaType,0) }
                    val endOfPaginationReached = movies?.isEmpty() ?: false
                    val prevPage = if (currentPage == 1) null else currentPage - 1
                    val nextPage = if (endOfPaginationReached) null else currentPage + 1
                    val remoteKeys = movies?.map { entity ->
                        MovieRemoteKeyEntity(
                            id = entity.id,
                            mediaType = mediaType,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }

                    databaseDataSource.handlePaging(
                        mediaType = mediaType,
                        movies = movies ?: emptyList(),
                        remoteKeys = remoteKeys ?: emptyList(),
                        shouldDeleteMoviesAndRemoteKeys = loadType == LoadType.REFRESH
                    )

                    coroutineScope {
                        movies?.forEach { movie ->
                            launch {
                                val detail = networkDataSource.getDetailMovie(movie.networkId)
                                if (detail is CinemaxResponse.Success) {
                                    databaseDataSource.updateMovieRuntime(
                                        id = movie.networkId,
                                        runtime = detail.value.runtime?:0
                                    )
                                }
                            }
                        }
                    }

                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

                }
                is CinemaxResponse.Failure -> {
                    MediatorResult.Error(Exception(response.error))
                }

                is CinemaxResponse.Loading -> {
                    MediatorResult.Success(endOfPaginationReached = false)
                }

            }

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeyEntity? = PagingUtils.getRemoteKeyClosestToCurrentPosition(state) { entity ->
        databaseDataSource.getRemoteKeyByIdAndMediaType(
            id = entity.networkId,
            mediaType = mediaType
        )
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeyEntity? = PagingUtils.getRemoteKeyForFirstItem(state) { entity ->
        databaseDataSource.getRemoteKeyByIdAndMediaType(
            id = entity.networkId,
            mediaType = mediaType
        )
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeyEntity? = PagingUtils.getRemoteKeyForLastItem(state) { entity ->
        databaseDataSource.getRemoteKeyByIdAndMediaType(
            id = entity.networkId,
            mediaType = mediaType
        )
    }

}
package com.example.core.data.repository.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.source.MovieDatabaseSource
import com.example.core.data.local.source.MovieRemoteMediator
import com.example.core.data.mapper.asMediaType
import com.example.core.data.mapper.asMovieEntity
import com.example.core.data.mapper.asMovieModel
import com.example.core.data.mapper.asNetworkMediaType
import com.example.core.data.mapper.listMap
import com.example.core.data.mapper.pagingMap
import com.example.core.data.network.PAGE_SIZE
import com.example.core.data.network.datasource.MovieNetworkDataSource
import com.example.core.data.network.datasource.MultiNetworkDataSource
import com.example.core.data.network.datasource.SearchPagingSource
import com.example.core.data.network.datasource.TvShowNetworkDataSource
import com.example.core.data.network.defaultPagingConfig
import com.example.core.data.network.networkBoundResource
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.MovieModel
import com.example.core.domain.repository.movie.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val databaseDataSource: MovieDatabaseSource,
    private val movieDataSource: MovieNetworkDataSource,
    private val tvShowNetworkDataSource: TvShowNetworkDataSource,
    private val multiDataSource : MultiNetworkDataSource
) : MovieRepository {

    override fun searchMovie(query: String): Flow<PagingData<MovieModel>> {
        return Pager(
            config = defaultPagingConfig,
            pagingSourceFactory = {
                SearchPagingSource(
                    query = query,
                    movieNetworkDataSource = movieDataSource,
                    tvShowNetworkDataSource =tvShowNetworkDataSource,
                    multiDataSource = multiDataSource
                )
            }
        ).flow
    }

    override fun getByMediaType(mediaTypeModel: MediaTypeModel.Movie): Flow<CinemaxResponse<List<MovieModel>>> {
        val mediaType = mediaTypeModel.asMediaType()
        return networkBoundResource(
            query = {  databaseDataSource.getByMediaType(
                mediaType = mediaType, pageSize = PAGE_SIZE
            ).listMap (MovieEntity::asMovieModel)
            },
            fetch = { movieDataSource.getByMediaType(mediaType.asNetworkMediaType()) },
            saveFetchResult = { response ->
                val result = response.results?: emptyList()
                databaseDataSource.deleteByMediaTypeAndInsertAll(
                    mediaType = mediaType,
                    movies = result.map { it.asMovieEntity(mediaType) }
                )
            },
        )
    }

    override fun getPagingByMediaType(mediaTypeModel: MediaTypeModel.Movie): Flow<PagingData<MovieModel>> {
        val mediaType = mediaTypeModel.asMediaType()
        return Pager(
            config = defaultPagingConfig,
            remoteMediator = MovieRemoteMediator(databaseDataSource, movieDataSource, mediaType),
            pagingSourceFactory = { databaseDataSource.getPagingByMediaType(mediaType) }
        ).flow.pagingMap(MovieEntity::asMovieModel)
    }

}
package com.example.core.data.network.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.mapper.asMovieModel
import com.example.core.data.network.Constants.DEFAULT_PAGE
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MovieModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    private val query: String,
    private val movieNetworkDataSource: MovieNetworkDataSource,
    private val tvShowNetworkDataSource: TvShowNetworkDataSource,
    private val multiDataSource: MultiNetworkDataSource
) : PagingSource<Int, MovieModel>() {

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        return try {
            val page = params.key ?: DEFAULT_PAGE

            when(val response = multiDataSource.searchMulti(page = page, query = query)){
                is CinemaxResponse.Loading -> LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
                is CinemaxResponse.Success -> {
                    val data = coroutineScope { response.value.results.filter { it.mediaType == "movie" || it.mediaType == "tv" }
                        .map { item ->
                            async {
                                val runtime = when (item.mediaType) {
                                    "movie" -> movieNetworkDataSource.getDetailMovie(item.id ?: 0)
                                        .let { if (it is CinemaxResponse.Success) it.value.runtime else 0 }

                                    "tv" -> tvShowNetworkDataSource.getDetailTv(item.id ?: 0)
                                        .let { if (it is CinemaxResponse.Success) it.value.numberOfSeasons else 0 }

                                    else -> 0
                                }
                                item.asMovieModel(runtime?:0)
                            }

                        }
                        }.awaitAll()

                    val endOfPaginationReached = data.isEmpty()

                    LoadResult.Page(
                        data = data,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (endOfPaginationReached) null else page + 1
                    )

                }
                is CinemaxResponse.Failure -> LoadResult.Error(Exception(response.error))

            }

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}


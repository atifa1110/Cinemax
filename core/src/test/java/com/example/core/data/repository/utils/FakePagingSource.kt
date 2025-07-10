package com.example.core.data.repository.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.model.tv.TvShowEntity

class FakeMoviePagingSource(
    private val data: List<MovieEntity>
) : PagingSource<Int, MovieEntity>() {
    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = null
        )
    }
}

class FakeTvPagingSource(
    private val data: List<TvShowEntity>
) : PagingSource<Int, TvShowEntity>() {
    override fun getRefreshKey(state: PagingState<Int, TvShowEntity>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowEntity> {
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = null
        )
    }
}

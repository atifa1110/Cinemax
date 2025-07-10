package com.example.core.domain.repository.movie

import androidx.paging.PagingData
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun searchMovie(query: String) : Flow<PagingData<MovieModel>>
    fun getByMediaType(mediaTypeModel: MediaTypeModel.Movie): Flow<CinemaxResponse<List<MovieModel>>>
    fun getPagingByMediaType(mediaTypeModel: MediaTypeModel.Movie): Flow<PagingData<MovieModel>>
}
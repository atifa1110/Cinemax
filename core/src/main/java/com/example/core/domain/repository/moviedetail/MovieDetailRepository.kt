package com.example.core.domain.repository.moviedetail

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MovieDetailModel
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun getDetailMovie(id: Int) : Flow<CinemaxResponse<MovieDetailModel?>>
}
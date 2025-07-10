package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.repository.moviedetail.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailMovieUseCase @Inject constructor(
    private val detailRepository : MovieDetailRepository
) {
    operator fun invoke(id : Int): Flow<CinemaxResponse<MovieDetailModel?>> {
        return detailRepository.getDetailMovie(id)
    }
}
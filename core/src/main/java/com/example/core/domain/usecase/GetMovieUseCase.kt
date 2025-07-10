package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.MovieModel
import com.example.core.domain.repository.movie.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(mediaTypeModel: MediaTypeModel.Movie): Flow<CinemaxResponse<List<MovieModel>>> {
        return movieRepository.getByMediaType(mediaTypeModel)
    }
}
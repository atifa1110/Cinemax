package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.MovieModel
import com.example.core.domain.repository.movie.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviePagingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(mediaTypeModel: MediaTypeModel.Movie) : Flow<PagingData<MovieModel>> {
        return movieRepository.getPagingByMediaType(mediaTypeModel)
    }
}
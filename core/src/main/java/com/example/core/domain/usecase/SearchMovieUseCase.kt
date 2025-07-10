package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.domain.model.MovieModel
import com.example.core.domain.repository.movie.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val searchRepository: MovieRepository
) {
    operator fun invoke(query: String): Flow<PagingData<MovieModel>> {
        return searchRepository.searchMovie(query)
    }
}
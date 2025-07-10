package com.example.core.domain.usecase

import com.example.core.domain.repository.search.SearchHistoryRepository
import javax.inject.Inject

class DeleteMovieFromSearchHistoryUseCase @Inject constructor(
    private val searchRepository: SearchHistoryRepository
) {
    suspend operator fun invoke(id: Int) {
        searchRepository.removeMovieFromSearchHistory(id)
    }
}
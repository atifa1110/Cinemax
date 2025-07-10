package com.example.core.domain.usecase

import com.example.core.domain.model.SearchModel
import com.example.core.domain.repository.search.SearchHistoryRepository
import javax.inject.Inject

class AddMovieToSearchHistoryUseCase @Inject constructor(
    private val searchRepository: SearchHistoryRepository
) {
    suspend operator fun invoke(searchModel: SearchModel) {
        searchRepository.addMovieToSearchHistory(searchModel)
    }
}
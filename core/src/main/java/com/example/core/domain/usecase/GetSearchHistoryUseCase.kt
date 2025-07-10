package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.SearchModel
import com.example.core.domain.repository.search.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val searchRepository: SearchHistoryRepository
) {
    operator fun invoke(): Flow<CinemaxResponse<List<SearchModel>>> {
        return searchRepository.getSearchHistory()
    }

}
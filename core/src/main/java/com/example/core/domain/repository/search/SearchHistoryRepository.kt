package com.example.core.domain.repository.search

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.SearchModel
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun getSearchHistory(): Flow<CinemaxResponse<List<SearchModel>>>
    suspend fun addMovieToSearchHistory(searchModel: SearchModel)
    suspend fun removeMovieFromSearchHistory(id: Int)
    suspend fun isSearchExist(id: Int) : Boolean
}
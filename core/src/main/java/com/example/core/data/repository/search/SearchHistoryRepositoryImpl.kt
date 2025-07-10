package com.example.core.data.repository.search

import com.example.core.data.local.model.search.SearchEntity
import com.example.core.data.local.source.SearchHistoryDatabaseSource
import com.example.core.data.mapper.asSearchEntity
import com.example.core.data.mapper.asSearchModel
import com.example.core.data.mapper.listMap
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.SearchModel
import com.example.core.domain.repository.search.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchDatabaseSource: SearchHistoryDatabaseSource
) : SearchHistoryRepository {

    override fun getSearchHistory(): Flow<CinemaxResponse<List<SearchModel>>> {
        return flow {
            try {
                emit(CinemaxResponse.Loading)
                val result = searchDatabaseSource.getSearchHistory().listMap(SearchEntity::asSearchModel).first()
                emit(CinemaxResponse.Success(result))
            } catch (e: Exception) {
                emit(CinemaxResponse.Failure(-1,e.localizedMessage ?: "Unexpected Error!"))
            }
        }
    }

    override suspend fun addMovieToSearchHistory(searchModel: SearchModel) {
        return searchDatabaseSource.addMovieToSearchHistory(searchModel.asSearchEntity())
    }

    override suspend fun removeMovieFromSearchHistory(id: Int) {
        return searchDatabaseSource.deleteMovieToSearchHistory(id)
    }

    override suspend fun isSearchExist(id: Int): Boolean {
        return searchDatabaseSource.isSearchExist(id)
    }
}
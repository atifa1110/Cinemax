package com.example.core.data.local.source

import com.example.core.data.local.dao.search.SearchDao
import com.example.core.data.local.model.search.SearchEntity
import javax.inject.Inject

class SearchHistoryDatabaseSource @Inject constructor(
    private val searchDao: SearchDao
) {
    fun getSearchHistory() = searchDao.getSearchHistory()
    suspend fun addMovieToSearchHistory(searchEntity: SearchEntity) = searchDao.insertSearchHistory(searchEntity)
    suspend fun deleteMovieToSearchHistory(id: Int) = searchDao.deleteSearchHistory(id)
    suspend fun isSearchExist(id: Int) = searchDao.isSearchExist(id)
}

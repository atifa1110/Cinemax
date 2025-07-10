package com.example.core.data.local.source

import com.example.core.data.local.dao.tv.TvShowDetailsDao
import com.example.core.data.local.model.tv.TvShowDetailsEntity
import com.example.core.data.local.util.DatabaseTransactionProvider
import javax.inject.Inject

class TvShowDetailsDatabaseSource @Inject constructor(
    private val tvShowDetailsDao: TvShowDetailsDao,
    private val transactionProvider: DatabaseTransactionProvider
) {
    fun getById(id: Int) = tvShowDetailsDao.getById(id)
    suspend fun deleteAndInsert(tvShowDetails: TvShowDetailsEntity) =
        transactionProvider.runWithTransaction {
            tvShowDetailsDao.deleteById(id = tvShowDetails.id)
            tvShowDetailsDao.insert(tvShowDetails)
        }
}
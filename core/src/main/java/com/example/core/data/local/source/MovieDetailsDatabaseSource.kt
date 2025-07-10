package com.example.core.data.local.source

import com.example.core.data.local.dao.movie.MovieDetailsDao
import com.example.core.data.local.model.detailMovie.MovieDetailsEntity
import com.example.core.data.local.util.DatabaseTransactionProvider
import javax.inject.Inject

class MovieDetailsDatabaseSource @Inject constructor(
    private val movieDetailsDao: MovieDetailsDao,
    private val transactionProvider: DatabaseTransactionProvider
) {
    fun getById(id: Int) = movieDetailsDao.getById(id)
    suspend fun deleteAndInsert(movieDetails: MovieDetailsEntity) =
        transactionProvider.runWithTransaction {
            movieDetailsDao.deleteById(id = movieDetails.id)
            movieDetailsDao.insert(movieDetails)
        }
}
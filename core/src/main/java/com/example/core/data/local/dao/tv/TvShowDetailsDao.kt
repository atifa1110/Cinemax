package com.example.core.data.local.dao.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.model.tv.TvShowDetailsEntity
import com.example.core.data.local.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDetailsDao {

    @Query("SELECT * FROM ${Constants.Tables.TV_SHOW_DETAILS} WHERE id = :id")
    fun getById(id: Int): Flow<TvShowDetailsEntity?>

    @Query("SELECT * FROM ${Constants.Tables.TV_SHOW_DETAILS} WHERE id IN (:ids)")
    fun getByIds(ids: List<Int>): Flow<List<TvShowDetailsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieDetails: TvShowDetailsEntity)

    @Query("DELETE FROM ${Constants.Tables.TV_SHOW_DETAILS} WHERE id = :id")
    suspend fun deleteById(id: Int)

}
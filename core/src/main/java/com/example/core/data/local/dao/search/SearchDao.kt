package com.example.core.data.local.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.model.search.SearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM search ORDER BY timestamp DESC")
    fun getSearchHistory(): Flow<List<SearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchEntity : SearchEntity)

    @Query("DELETE FROM search WHERE id = :id")
    suspend fun deleteSearchHistory(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM search WHERE id = :id)")
    suspend fun isSearchExist(id: Int): Boolean
}
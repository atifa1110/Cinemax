package com.example.core.data.local.dao.genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.model.movie.GenreEntity
import com.example.core.data.local.util.Constants.Tables.GENRES
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<GenreEntity>)

    @Query("SELECT * FROM $GENRES")
    fun getGenres(): Flow<List<GenreEntity>>

    @Query("SELECT * FROM $GENRES WHERE id IN (:genreIds)")
    fun getGenresByIds(genreIds: List<Int>): Flow<List<GenreEntity>>

    @Query("SELECT name FROM $GENRES WHERE id = :genreId LIMIT 1")
    suspend fun getGenreNameById(genreId: Int): String?

    @Query("DELETE FROM $GENRES")
    suspend fun deleteAll()
}
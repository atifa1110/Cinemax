package com.example.core.data.local.dao.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.util.Constants.Tables.MOVIES
import com.example.core.data.local.util.DatabaseMediaType
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM $MOVIES WHERE media_type = :mediaType LIMIT :pageSize")
    fun getByMediaType(mediaType: DatabaseMediaType.Movie, pageSize: Int): Flow<List<MovieEntity>>

    @Query("SELECT * FROM $MOVIES WHERE media_type = :mediaType")
    fun getPagingByMediaType(mediaType: DatabaseMediaType.Movie): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM $MOVIES WHERE media_type = :mediaType")
    suspend fun deleteByMediaType(mediaType: DatabaseMediaType.Movie)

    @Query("UPDATE $MOVIES SET runtime = :runtime WHERE network_id = :id")
    suspend fun updateRuntime(id: Int, runtime: Int)
}
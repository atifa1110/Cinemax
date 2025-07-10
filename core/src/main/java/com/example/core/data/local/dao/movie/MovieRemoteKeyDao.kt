package com.example.core.data.local.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.model.movie.MovieRemoteKeyEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.local.util.Constants.Tables.MOVIES_REMOTE_KEYS

@Dao
interface MovieRemoteKeyDao {
    @Query("SELECT * FROM $MOVIES_REMOTE_KEYS WHERE id = :id AND media_type = :mediaType")
    suspend fun getByIdAndMediaType(id: Int, mediaType: DatabaseMediaType.Movie): MovieRemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<MovieRemoteKeyEntity>)

    @Query("DELETE FROM $MOVIES_REMOTE_KEYS WHERE media_type = :mediaType")
    suspend fun deleteByMediaType(mediaType: DatabaseMediaType.Movie)
}
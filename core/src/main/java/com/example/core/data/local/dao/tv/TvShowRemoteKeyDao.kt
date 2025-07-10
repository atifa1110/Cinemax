package com.example.core.data.local.dao.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.model.tv.TvShowRemoteKeyEntity
import com.example.core.data.local.util.Constants
import com.example.core.data.local.util.DatabaseMediaType

@Dao
interface TvShowRemoteKeyDao {
    @Query("SELECT * FROM ${Constants.Tables.TV_SHOWS_REMOTE_KEYS} WHERE id = :id AND media_type = :mediaType")
    suspend fun getByIdAndMediaType(id: Int, mediaType: DatabaseMediaType.TvShow): TvShowRemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<TvShowRemoteKeyEntity>)

    @Query("DELETE FROM ${Constants.Tables.TV_SHOWS_REMOTE_KEYS} WHERE media_type = :mediaType")
    suspend fun deleteByMediaType(mediaType: DatabaseMediaType.TvShow)
}
package com.example.core.data.local.dao.tv

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.model.tv.TvShowEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.local.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {
    @Query("SELECT * FROM ${Constants.Tables.TV_SHOWS} WHERE media_type = :mediaType LIMIT :pageSize")
    fun getByMediaType(mediaType: DatabaseMediaType.TvShow, pageSize: Int): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM ${Constants.Tables.TV_SHOWS} WHERE media_type = :mediaType")
    fun getPagingByMediaType(mediaType: DatabaseMediaType.TvShow): PagingSource<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tvShows: List<TvShowEntity>)

    @Query("DELETE FROM ${Constants.Tables.TV_SHOWS} WHERE media_type = :mediaType")
    suspend fun deleteByMediaType(mediaType: DatabaseMediaType.TvShow)

    @Query("UPDATE ${Constants.Tables.TV_SHOWS} SET seasons = :seasons WHERE network_id = :id")
    suspend fun updateSeasons(id: Int, seasons: Int)
}
package com.example.core.data.local.dao.wishlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.model.wishlist.WishlistEntity
import com.example.core.data.local.util.Constants
import com.example.core.data.local.util.DatabaseMediaType
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
    @Query("SELECT * FROM ${Constants.Tables.WISHLIST}")
    fun getByMediaType(): Flow<List<WishlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wishlist: WishlistEntity)

    @Query("DELETE FROM ${Constants.Tables.WISHLIST} WHERE media_type = :mediaType AND network_id = :id")
    suspend fun deleteByMediaTypeAndNetworkId(mediaType: DatabaseMediaType.Wishlist, id: Int)

    @Query("SELECT EXISTS(SELECT * FROM ${Constants.Tables.WISHLIST} WHERE media_type = :mediaType AND network_id = :id)")
    suspend fun isWishListed(mediaType: DatabaseMediaType.Wishlist, id: Int): Boolean
}
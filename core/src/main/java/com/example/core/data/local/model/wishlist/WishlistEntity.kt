package com.example.core.data.local.model.wishlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.local.model.movie.GenreEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.local.util.Constants

@Entity(tableName = Constants.Tables.WISHLIST)
data class WishlistEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Fields.ID)
    val id: Int = 0,

    @ColumnInfo(name = Constants.Fields.NETWORK_ID)
    val networkId: Int,

    @ColumnInfo(name = Constants.Fields.MEDIA_TYPE)
    val mediaType: DatabaseMediaType.Wishlist,

    @ColumnInfo(name = Constants.Fields.TITLE)
    val title: String,

    @ColumnInfo(name = Constants.Fields.GENRE_IDS)
    val genreEntities: List<GenreEntity>?,

    @ColumnInfo(name = Constants.Fields.RATING)
    val rating: Double,

    @ColumnInfo(name = Constants.Fields.POSTER_PATH)
    val posterPath: String,

    @ColumnInfo(name = Constants.Fields.WISHLISTED)
    val isWishListed: Boolean
)
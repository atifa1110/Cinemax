package com.example.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.data.local.converter.CreditsConverter
import com.example.core.data.local.converter.ImagesConverter
import com.example.core.data.local.converter.ListConverter
import com.example.core.data.local.converter.VideosConverter
import com.example.core.data.local.dao.genre.GenreDao
import com.example.core.data.local.dao.movie.MovieDao
import com.example.core.data.local.dao.movie.MovieDetailsDao
import com.example.core.data.local.dao.movie.MovieRemoteKeyDao
import com.example.core.data.local.dao.search.SearchDao
import com.example.core.data.local.dao.tv.TvShowDao
import com.example.core.data.local.dao.tv.TvShowDetailsDao
import com.example.core.data.local.dao.tv.TvShowRemoteKeyDao
import com.example.core.data.local.dao.wishlist.WishlistDao
import com.example.core.data.local.model.detailMovie.MovieDetailsEntity
import com.example.core.data.local.model.movie.GenreEntity
import com.example.core.data.local.model.movie.MovieEntity
import com.example.core.data.local.model.movie.MovieRemoteKeyEntity
import com.example.core.data.local.model.search.SearchEntity
import com.example.core.data.local.model.tv.TvShowDetailsEntity
import com.example.core.data.local.model.tv.TvShowEntity
import com.example.core.data.local.model.tv.TvShowRemoteKeyEntity
import com.example.core.data.local.model.wishlist.WishlistEntity

@Database(
    entities = [
        MovieEntity::class, MovieRemoteKeyEntity::class,
        MovieDetailsEntity::class, TvShowEntity::class,
        TvShowRemoteKeyEntity::class, TvShowDetailsEntity::class,
        WishlistEntity::class, SearchEntity::class,
        GenreEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(ListConverter::class, CreditsConverter::class, ImagesConverter::class,
    VideosConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeyDao(): MovieRemoteKeyDao
    abstract fun movieDetailsDao(): MovieDetailsDao

    abstract fun tvShowDao(): TvShowDao
    abstract fun tvShowRemoteKeyDao(): TvShowRemoteKeyDao
    abstract fun tvShowDetailsDao(): TvShowDetailsDao

    abstract fun wishListDao() : WishlistDao
    abstract fun searchHistoryDao() : SearchDao
    abstract fun genreDao() : GenreDao

    companion object {
        const val MOVIE_DATABASE = "cinemax.db"
    }
}
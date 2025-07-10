package com.example.cinemax.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.local.dao.genre.GenreDao
import com.example.core.data.local.dao.movie.MovieDao
import com.example.core.data.local.dao.movie.MovieDetailsDao
import com.example.core.data.local.dao.movie.MovieRemoteKeyDao
import com.example.core.data.local.dao.search.SearchDao
import com.example.core.data.local.dao.tv.TvShowDao
import com.example.core.data.local.dao.tv.TvShowDetailsDao
import com.example.core.data.local.dao.tv.TvShowRemoteKeyDao
import com.example.core.data.local.dao.wishlist.WishlistDao
import com.example.core.data.local.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideEncryptedDatabase(@ApplicationContext context: Context): MovieDatabase {
        val passphrase = PassphraseManager.getOrCreatePassphrase(context)
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MovieDatabase.MOVIE_DATABASE
        ).openHelperFactory(factory).fallbackToDestructiveMigration(false)
            .build()
    }

//    @Singleton
//    @Provides
//    fun provideDataBase(@ApplicationContext context: Context): MovieDatabase {
//        return Room.databaseBuilder(
//            context.applicationContext,
//            MovieDatabase::class.java,
//            MovieDatabase.MOVIE_DATABASE
//        ).build()
//    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()

    @Provides
    fun provideMovieRemoteKeyDao(database: MovieDatabase): MovieRemoteKeyDao = database.movieRemoteKeyDao()

    @Provides
    fun provideMovieDetailDao(database: MovieDatabase): MovieDetailsDao = database.movieDetailsDao()

    @Provides
    fun provideWishlistDao(database: MovieDatabase): WishlistDao = database.wishListDao()

    @Provides
    fun provideSearchDao(database: MovieDatabase): SearchDao = database.searchHistoryDao()

    @Provides
    fun provideGenreDao(database: MovieDatabase): GenreDao = database.genreDao()

    @Provides
    fun provideTvShowDao(database: MovieDatabase): TvShowDao = database.tvShowDao()

    @Provides
    fun provideTvShowRemoteKeyDao(database: MovieDatabase): TvShowRemoteKeyDao = database.tvShowRemoteKeyDao()

    @Provides
    fun provideTvShowDetailDao(database: MovieDatabase): TvShowDetailsDao = database.tvShowDetailsDao()
}

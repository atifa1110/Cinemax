package com.example.cinemax.di

import com.example.core.data.repository.auth.AuthRepositoryImpl
import com.example.core.data.repository.datastore.DataStoreRepositoryImpl
import com.example.core.data.repository.movie.MovieRepositoryImpl
import com.example.core.data.repository.moviedetail.MovieDetailRepositoryImpl
import com.example.core.data.repository.search.SearchHistoryRepositoryImpl
import com.example.core.data.repository.storage.StorageRepositoryImpl
import com.example.core.data.repository.tv.TvShowRepositoryImpl
import com.example.core.data.repository.tvdetail.TvShowDetailRepositoryImpl
import com.example.core.data.repository.wishlist.WishListRepositoryImpl
import com.example.core.domain.repository.auth.AuthRepository
import com.example.core.domain.repository.datastore.DataStoreRepository
import com.example.core.domain.repository.movie.MovieRepository
import com.example.core.domain.repository.moviedetail.MovieDetailRepository
import com.example.core.domain.repository.search.SearchHistoryRepository
import com.example.core.domain.repository.storage.StorageRepository
import com.example.core.domain.repository.tv.TvShowRepository
import com.example.core.domain.repository.tvdetail.TvShowDetailRepository
import com.example.core.domain.repository.wishlist.WishListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Singleton
    @Binds
    abstract fun bindTvShowRepository(repository: TvShowRepositoryImpl): TvShowRepository

    @Singleton
    @Binds
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindMovieDetailRepository(repository: MovieDetailRepositoryImpl): MovieDetailRepository

    @Singleton
    @Binds
    abstract fun bindTvShowDetailRepository(repository: TvShowDetailRepositoryImpl): TvShowDetailRepository

    @Singleton
    @Binds
    abstract fun bindWishlistRepository(repository: WishListRepositoryImpl): WishListRepository

    @Singleton
    @Binds
    abstract fun bindSearchHistoryRepository(repository: SearchHistoryRepositoryImpl): SearchHistoryRepository

    @Singleton
    @Binds
    abstract fun bindDataStoreRepository(repository: DataStoreRepositoryImpl): DataStoreRepository

    @Singleton
    @Binds
    abstract fun bindDataStorageRepository(repository: StorageRepositoryImpl): StorageRepository
}
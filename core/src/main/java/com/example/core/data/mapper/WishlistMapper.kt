package com.example.core.data.mapper

import com.example.core.data.local.model.wishlist.WishlistEntity
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.model.TvShowDetailModel
import com.example.core.domain.model.WishlistModel


fun WishlistEntity.asWishlistModel() = WishlistModel(
    id = networkId,
    mediaType = mediaType.asMediaTypeModel(),
    title = title,
    genre = genreEntities?.asGenreModel(),
    rating = rating,
    posterPath = posterPath,
    isWishListed = isWishListed
)

internal fun DatabaseMediaType.Wishlist.asWishlistEntity(movie: MovieDetailModel) = WishlistEntity(
    mediaType = this,
    networkId = movie.id,
    title = movie.title,
    genreEntities = movie.genres.asGenreEntity(),
    rating = movie.rating,
    posterPath = movie.posterPath.toString(),
    isWishListed = movie.isWishListed
)

internal fun DatabaseMediaType.Wishlist.asWishlistEntity(tvShow : TvShowDetailModel) = WishlistEntity(
    mediaType = this,
    networkId = tvShow.id,
    title = tvShow.name,
    genreEntities = tvShow.genres.asGenreEntity(),
    rating = tvShow.rating,
    posterPath = tvShow.posterPath.toString(),
    isWishListed = tvShow.isWishListed
)
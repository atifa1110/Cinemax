package com.example.core.data.mapper

import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.network.NetworkMediaType
import com.example.core.domain.model.MediaTypeModel

fun MediaTypeModel.Movie.asMediaType() = DatabaseMediaType.Movie[mediaType]
fun MediaTypeModel.TvShow.asMediaType() = DatabaseMediaType.TvShow[mediaType]

fun DatabaseMediaType.Movie.asNetworkMediaType() = NetworkMediaType.Movie[mediaType]
fun DatabaseMediaType.TvShow.asNetworkMediaType() = NetworkMediaType.TvShow[mediaType]

fun DatabaseMediaType.Wishlist.asMediaTypeModel() = when (this) {
    DatabaseMediaType.Wishlist.Movie -> MediaTypeModel.Wishlist.Movie
    DatabaseMediaType.Wishlist.TvShow -> MediaTypeModel.Wishlist.TvShow
}
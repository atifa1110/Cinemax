package com.example.core.ui.mapper

import com.example.core.domain.model.WishlistModel
import com.example.core.ui.model.WishList

fun WishlistModel.asWishlist() = WishList(
    id = id,
    mediaType = mediaType.asMediaTypeModel(),
    genres = genre?.asGenres(),
    title = title,
    rating = rating,
    posterPath = posterPath,
    isWishListed = isWishListed
)
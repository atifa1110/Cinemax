package com.example.core.ui.model

data class WishList(
    val id: Int,
    val mediaType: MediaType.Wishlist,
    val title : String,
    val genres : List<Genre>?,
    val rating : Double,
    val posterPath : String,
    val isWishListed : Boolean
)
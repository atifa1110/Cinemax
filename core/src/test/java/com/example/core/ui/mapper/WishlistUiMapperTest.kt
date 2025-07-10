package com.example.core.ui.mapper

import com.example.core.domain.model.GenreModel
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.WishlistModel
import org.junit.Assert.assertEquals
import org.junit.Test

class WishlistUiMapperTest {

    @Test
    fun `asWishlist maps WishlistModel to WishList correctly`() {
        val wishlistModel = WishlistModel(
            id = 101,
            mediaType = MediaTypeModel.Wishlist.Movie,
            genre = listOf(GenreModel.ACTION, GenreModel.DRAMA),
            title = "Inception",
            rating = 8.7,
            posterPath = "/somepath.jpg",
            isWishListed = true
        )

        val result = wishlistModel.asWishlist()

        assertEquals(wishlistModel.id, result.id)
        assertEquals("Movie", result.mediaType.name)
        assertEquals(wishlistModel.title, result.title)
        assertEquals(wishlistModel.rating, result.rating, 0.01)
        assertEquals(wishlistModel.posterPath, result.posterPath)
        assertEquals(wishlistModel.isWishListed, result.isWishListed)
        assertEquals(2, result.genres?.size)
    }
}

package com.example.core.data.mapper

import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.network.NetworkMediaType
import com.example.core.domain.model.MediaTypeModel
import kotlin.test.Test
import kotlin.test.assertEquals

class MediaTypeMapperTest {

    @Test
    fun `MediaTypeModel Movie maps to DatabaseMediaType Movie`() {
        val model = MediaTypeModel.Movie.Upcoming
        val result = model.asMediaType()
        assertEquals(DatabaseMediaType.Movie.Upcoming, result)
    }

    @Test
    fun `MediaTypeModel TvShow maps to DatabaseMediaType TvShow`() {
        val model = MediaTypeModel.TvShow.TopRated
        val result = model.asMediaType()
        assertEquals(DatabaseMediaType.TvShow.TopRated, result)
    }

    @Test
    fun `DatabaseMediaType Movie maps to NetworkMediaType Movie`() {
        val dbType = DatabaseMediaType.Movie.NowPlaying
        val result = dbType.asNetworkMediaType()
        assertEquals(NetworkMediaType.Movie.NOW_PLAYING, result)
    }

    @Test
    fun `DatabaseMediaType TvShow maps to NetworkMediaType TvShow`() {
        val dbType = DatabaseMediaType.TvShow.Popular
        val result = dbType.asNetworkMediaType()
        assertEquals(NetworkMediaType.TvShow.POPULAR, result)
    }

    @Test
    fun `DatabaseMediaType Wishlist maps to MediaTypeModel Wishlist`() {
        val movieWishlist = DatabaseMediaType.Wishlist.Movie
        val tvWishlist = DatabaseMediaType.Wishlist.TvShow

        assertEquals(MediaTypeModel.Wishlist.Movie, movieWishlist.asMediaTypeModel())
        assertEquals(MediaTypeModel.Wishlist.TvShow, tvWishlist.asMediaTypeModel())
    }
}

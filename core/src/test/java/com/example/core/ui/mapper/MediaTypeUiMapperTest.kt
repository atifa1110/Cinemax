package com.example.core.ui.mapper

import com.example.core.domain.model.MediaTypeModel
import com.example.core.ui.model.MediaType
import org.junit.Assert.assertEquals
import org.junit.Test


class MediaTypeUiMapperTest {

    @Test
    fun `Movie media type should map to MediaTypeModel correctly`() {
        assertEquals(MediaTypeModel.Movie.Upcoming, MediaType.Movie.Upcoming.asMediaTypeModel())
        assertEquals(MediaTypeModel.Movie.Popular, MediaType.Movie.Popular.asMediaTypeModel())
    }

    @Test
    fun `TvShow media type should map to MediaTypeModel correctly`() {
        assertEquals(MediaTypeModel.TvShow.Popular, MediaType.TvShow.Popular.asMediaTypeModel())
        assertEquals(MediaTypeModel.TvShow.TopRated, MediaType.TvShow.TopRated.asMediaTypeModel())
    }

    @Test
    fun `Common movie media type should map to Movie media type correctly`() {
        assertEquals(MediaType.Movie.Upcoming, MediaType.Common.Movie.Upcoming.asMovieMediaType())
        assertEquals(MediaType.Movie.Trending, MediaType.Common.Movie.Trending.asMovieMediaType())
    }

    @Test
    fun `Common tv show media type should map to TvShow media type correctly`() {
        assertEquals(MediaType.TvShow.Trending, MediaType.Common.TvShow.Trending.asTvShowMediaType())
    }

    @Test
    fun `Wishlist media type model should map to wishlist UI type correctly`() {
        assertEquals(MediaType.Wishlist.Movie, MediaTypeModel.Wishlist.Movie.asMediaTypeModel())
        assertEquals(MediaType.Wishlist.TvShow, MediaTypeModel.Wishlist.TvShow.asMediaTypeModel())
    }
}
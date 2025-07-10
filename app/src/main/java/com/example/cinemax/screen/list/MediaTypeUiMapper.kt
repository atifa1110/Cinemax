package com.example.cinemax.screen.list

import androidx.annotation.StringRes
import com.example.cinemax.R
import com.example.core.ui.model.MediaType

@StringRes
internal fun MediaType.Common.asTitleResourceId(): Int = mediaTypesTitleResources.getValue(this)

// 🔹 Updated map to support both Movie & TV Show
private val mediaTypesTitleResources = mapOf(
    // 🎬 Movie Titles
    MediaType.Common.Movie.Upcoming to R.string.upcoming_movies,
    //MediaType.Common.Movie.TopRated to R.string.top_rated,
    MediaType.Common.Movie.Popular to R.string.most_popular_movie,
    MediaType.Common.Movie.NowPlaying to R.string.now_playing_movie,
    //MediaType.Common.Movie.Discover to R.string.discover,
    MediaType.Common.Movie.Trending to R.string.recommendation_movie,

    // 📺 TV Show Titles
    //MediaType.Common.TvShow.Upcoming to R.string.upcoming_tv_shows,
   // MediaType.Common.TvShow.TopRated to R.string.top_rated_tv,
    MediaType.Common.TvShow.Popular to R.string.most_popular_tv,
    MediaType.Common.TvShow.NowPlaying to R.string.now_playing_tv,
    //MediaType.Common.TvShow.Discover to R.string.discover_tv,
    MediaType.Common.TvShow.Trending to R.string.recommendation_tv
)

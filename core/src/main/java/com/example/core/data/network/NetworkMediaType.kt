package com.example.core.data.network

sealed interface NetworkMediaType {
    enum class Movie(val mediaType: String) : NetworkMediaType {
        UPCOMING(MOVIE_UPCOMING_MEDIA_TYPE),
        TOP_RATED(MOVIE_TOP_RATED_MEDIA_TYPE),
        POPULAR(MOVIE_POPULAR_MEDIA_TYPE),
        NOW_PLAYING(MOVIE_NOW_PLAYING_MEDIA_TYPE),
        //DISCOVER(DISCOVER_MEDIA_TYPE),
        TRENDING(MOVIE_TRENDING_MEDIA_TYPE);

        companion object {
            private val mediaTypes = entries.associateBy(Movie::mediaType)
            operator fun get(mediaType: String) = checkNotNull(mediaTypes[mediaType]) {
                "$INVALID_MEDIA_TYPE_MESSAGE $mediaType"
            }
        }
    }

    enum class TvShow(val mediaType: String) : NetworkMediaType {
        TOP_RATED(TOP_RATED_MEDIA_TYPE),
        POPULAR(POPULAR_MEDIA_TYPE),
        NOW_PLAYING(NOW_PLAYING_MEDIA_TYPE),
        DISCOVER(DISCOVER_MEDIA_TYPE),
        TRENDING(TRENDING_MEDIA_TYPE);

        companion object {
            private val mediaTypes = entries.associateBy(TvShow::mediaType)
            operator fun get(mediaType: String) = checkNotNull(mediaTypes[mediaType]) {
                "$INVALID_MEDIA_TYPE_MESSAGE $mediaType"
            }
        }
    }
}

private const val UPCOMING_MEDIA_TYPE = "upcoming"
private const val TOP_RATED_MEDIA_TYPE = "top_rated"
private const val POPULAR_MEDIA_TYPE = "popular"
private const val NOW_PLAYING_MEDIA_TYPE = "now_playing"
private const val DISCOVER_MEDIA_TYPE = "discover"
private const val TRENDING_MEDIA_TYPE = "trending"

private const val MOVIE_UPCOMING_MEDIA_TYPE = "movie_upcoming"
private const val MOVIE_TOP_RATED_MEDIA_TYPE = "movie_top_rated"
private const val MOVIE_POPULAR_MEDIA_TYPE = "movie_popular"
private const val MOVIE_NOW_PLAYING_MEDIA_TYPE = "movie_now_playing"
private const val MOVIE_DISCOVER_MEDIA_TYPE = "movie_discover"
private const val MOVIE_TRENDING_MEDIA_TYPE = "movie_trending"

private const val INVALID_MEDIA_TYPE_MESSAGE = "Invalid media type."
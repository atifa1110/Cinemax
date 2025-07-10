package com.example.core.ui.model

sealed interface MediaType {
    enum class Movie(val mediaType: String) : MediaType {
        Upcoming(MovieUpcomingMediaType),
        TopRated(MovieTopRatedMediaType),
        Popular(MoviePopularMediaType),
        NowPlaying(MovieNowPlayingMediaType),
        Discover(MovieDiscoverMediaType),
        Trending(MovieTrendingMediaType);

        companion object {
            private val mediaTypes = entries.associateBy(Movie::mediaType)
            operator fun get(mediaType: String) = checkNotNull(mediaTypes[mediaType]) {
                "$InvalidMediaTypeMessage $mediaType"
            }
        }
    }

    enum class TvShow(val mediaType: String) : MediaType {
        TopRated(TopRatedMediaType),
        Popular(PopularMediaType),
        NowPlaying(NowPlayingMediaType),
        Discover(DiscoverMediaType),
        Trending(TrendingMediaType);

        companion object {
            private val mediaTypes = entries.associateBy(TvShow::mediaType)
            operator fun get(mediaType: String) = checkNotNull(mediaTypes[mediaType]) {
                "$InvalidMediaTypeMessage $mediaType"
            }
        }
    }

    sealed class Common(val mediaType: String) : MediaType {

        sealed class Movie(mediaType: String) : Common(mediaType) {
            object Popular : Movie(MoviePopularMediaType)
            object Upcoming : Movie(MovieUpcomingMediaType)
            object NowPlaying: Movie(MovieNowPlayingMediaType)
            object Trending: Movie(MovieTrendingMediaType)
        }

        sealed class TvShow(mediaType: String) : Common(mediaType) {
            object Popular : TvShow(TvPopularMediaType)
            object NowPlaying : TvShow(TvNowPlayingMediaType)
            object Trending: Movie(TvTrendingMediaType)
        }

        companion object {
            fun from(mediaType: String): Common? {
                return when (mediaType) {
                    MoviePopularMediaType -> Movie.Popular
                    MovieUpcomingMediaType -> Movie.Upcoming
                    MovieNowPlayingMediaType -> Movie.NowPlaying
                    MovieTrendingMediaType -> Movie.Trending
                    TvPopularMediaType -> TvShow.Popular
                    TvNowPlayingMediaType -> TvShow.NowPlaying
                    TvTrendingMediaType -> TvShow.Trending
                    else -> null // Handle invalid media types
                }
            }
        }
    }

    enum class Wishlist { Movie, TvShow }

    sealed class Details(val mediaId: Int, val mediaType: String) : MediaType {
        data class Movie(val id: Int) : Details(mediaId = id, mediaType = MovieMediaType)
        data class TvShow(val id: Int) : Details(mediaId = id, mediaType = TvShowMediaType)
        data class Trailers(val id: Int) : Details(mediaId = id, mediaType = TrailerMediaType)

        companion object {
            fun from(id: Int, mediaType: String) = when (mediaType) {
                MovieMediaType -> Movie(id = id)
                TvShowMediaType -> TvShow(id = id)
                TrailerMediaType -> Trailers(id = id)
                else -> error("$InvalidMediaTypeMessage $mediaType")
            }
        }
    }
}

private const val MovieMediaType = "movie"
private const val TvShowMediaType = "tv_show"
private const val TrailerMediaType = "trailers"

private const val UpcomingMediaType = "upcoming"
private const val TopRatedMediaType = "top_rated"
private const val PopularMediaType = "popular"
private const val NowPlayingMediaType = "now_playing"
private const val DiscoverMediaType = "discover"
private const val TrendingMediaType = "trending"

private const val MovieUpcomingMediaType = "movie_upcoming"
private const val MovieTopRatedMediaType = "movie_top_rated"
private const val MoviePopularMediaType = "movie_popular"
private const val MovieNowPlayingMediaType = "movie_now_playing"
private const val MovieDiscoverMediaType = "movie_discover"
private const val MovieTrendingMediaType = "movie_trending"

private const val TvUpcomingMediaType = "tv_show_upcoming"
private const val TvTopRatedMediaType = "tv_show_top_rated"
private const val TvPopularMediaType = "tv_show_popular"
private const val TvNowPlayingMediaType = "tv_show_now_playing"
private const val TvDiscoverMediaType = "tv_show_discover"
private const val TvTrendingMediaType = "tv_show_trending"

private const val InvalidMediaTypeMessage = "Invalid media type."
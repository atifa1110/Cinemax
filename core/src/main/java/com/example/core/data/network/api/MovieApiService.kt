package com.example.core.data.network.api

import com.example.core.data.network.Constants
import com.example.core.data.network.model.movie.MovieDetailNetwork
import com.example.core.data.network.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET(Constants.Path.TRENDING_MOVIE)
    suspend fun getTrendingMovie(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): Response<MovieResponse>

    @GET(Constants.Path.NOW_PLAYING_MOVIE)
    suspend fun getNowPlayingMovie(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): Response<MovieResponse>

    @GET(Constants.Path.POPULAR_MOVIE)
    suspend fun getPopularMovie(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): Response<MovieResponse>

    @GET(Constants.Path.TOP_RATED_MOVIE)
    suspend fun getTopRatedMovie(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): Response<MovieResponse>

    @GET(Constants.Path.UPCOMING_MOVIE)
    suspend fun getUpcomingMovie(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): Response<MovieResponse>

    @GET(Constants.Path.DETAILS_MOVIE)
    suspend fun getDetailsById(
        @Path(Constants.Fields.ID) id: Int,
        @Query(Constants.Fields.APPEND_TO_RESPONSE)
        appendToResponse: String = Constants.Fields.DETAILS_APPEND_TO_RESPONSE
    ): Response<MovieDetailNetwork>

}
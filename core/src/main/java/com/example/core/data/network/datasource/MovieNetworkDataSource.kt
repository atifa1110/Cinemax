package com.example.core.data.network.datasource

import com.example.core.data.network.BaseRemoteDataSource
import com.example.core.data.network.Constants
import com.example.core.data.network.NetworkMediaType
import com.example.core.data.network.api.MovieApiService
import com.example.core.data.network.model.movie.MovieDetailNetwork
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.response.MovieResponse
import com.google.gson.Gson
import javax.inject.Inject

class MovieNetworkDataSource @Inject constructor(
    private val movieService: MovieApiService,
    private val gson : Gson
) : BaseRemoteDataSource(gson){

    suspend fun getByMediaType(
        mediaType: NetworkMediaType.Movie,
        page: Int = Constants.DEFAULT_PAGE
    ): CinemaxResponse<MovieResponse> {
        return safeApiCall {
            when (mediaType) {
                NetworkMediaType.Movie.UPCOMING -> movieService.getUpcomingMovie(page)
                NetworkMediaType.Movie.TOP_RATED -> movieService.getTopRatedMovie(page)
                NetworkMediaType.Movie.POPULAR -> movieService.getPopularMovie(page)
                NetworkMediaType.Movie.NOW_PLAYING -> movieService.getNowPlayingMovie(page)
                NetworkMediaType.Movie.TRENDING -> movieService.getTrendingMovie(page)
            }
        }
    }

    suspend fun getDetailMovie(
        id: Int,
        appendToResponse: String = Constants.Fields.DETAILS_APPEND_TO_RESPONSE
    ): CinemaxResponse<MovieDetailNetwork> {
        return safeApiCall {
            movieService.getDetailsById(id, appendToResponse)
        }
    }
}
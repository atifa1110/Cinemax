package com.example.core.data.network.api

import com.example.core.data.network.Constants
import com.example.core.data.network.model.tv.TvShowDetailNetwork
import com.example.core.data.network.response.TvShowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApiService {
    @GET(Constants.Path.TOP_RATED_TV_SHOW)
    suspend fun getTopRatedTv(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): Response<TvShowResponse>

    @GET(Constants.Path.POPULAR_TV_SHOW)
    suspend fun getPopularTv(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): Response<TvShowResponse>

    @GET(Constants.Path.ON_THE_AIR_TV_SHOW)
    suspend fun getOnTheAirTv(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): Response<TvShowResponse>

    @GET(Constants.Path.DISCOVER_TV_SHOW)
    suspend fun getDiscoverTv(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): Response<TvShowResponse>

    @GET(Constants.Path.TRENDING_TV_SHOW)
    suspend fun getTrendingTv(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): Response<TvShowResponse>

    @GET(Constants.Path.DETAILS_TV_SHOW)
    suspend fun getDetailsById(
        @Path(Constants.Fields.ID)
        id: Int,
        @Query(Constants.Fields.APPEND_TO_RESPONSE)
        appendToResponse: String = Constants.Fields.DETAILS_APPEND_TO_RESPONSE_TV
    ): Response<TvShowDetailNetwork>
}

package com.example.core.data.network.datasource

import com.example.core.data.network.BaseRemoteDataSource
import com.example.core.data.network.Constants
import com.example.core.data.network.NetworkMediaType
import com.example.core.data.network.api.TvShowApiService
import com.example.core.data.network.model.tv.TvShowDetailNetwork
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.response.TvShowResponse
import com.google.gson.Gson
import javax.inject.Inject

class TvShowNetworkDataSource @Inject constructor(
    private val tvShowApiService: TvShowApiService,
    private val gson: Gson
) : BaseRemoteDataSource(gson){

    suspend fun getByMediaType(
        mediaType: NetworkMediaType.TvShow,
        page: Int = Constants.DEFAULT_PAGE
    ): CinemaxResponse<TvShowResponse> {
        return safeApiCall {
            when (mediaType) {
                NetworkMediaType.TvShow.DISCOVER -> tvShowApiService.getDiscoverTv(page)
                NetworkMediaType.TvShow.TOP_RATED -> tvShowApiService.getTopRatedTv(page)
                NetworkMediaType.TvShow.POPULAR -> tvShowApiService.getPopularTv(page)
                NetworkMediaType.TvShow.NOW_PLAYING -> tvShowApiService.getOnTheAirTv(page)
                NetworkMediaType.TvShow.TRENDING -> tvShowApiService.getTrendingTv(page)
            }
        }
    }

    suspend fun getDetailTv(
        id: Int,
        appendToResponse: String = Constants.Fields.DETAILS_APPEND_TO_RESPONSE_TV
    ): CinemaxResponse<TvShowDetailNetwork> {
        return safeApiCall {
            tvShowApiService.getDetailsById(id, appendToResponse)
        }
    }
}
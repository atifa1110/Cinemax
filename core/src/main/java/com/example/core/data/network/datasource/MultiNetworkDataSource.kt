package com.example.core.data.network.datasource

import com.example.core.data.network.BaseRemoteDataSource
import com.example.core.data.network.Constants
import com.example.core.data.network.api.MultiApiService
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.response.MultiResponse
import com.google.gson.Gson
import javax.inject.Inject


class MultiNetworkDataSource @Inject constructor(
    private val multiApiService: MultiApiService,
    private val gson: Gson
) : BaseRemoteDataSource(gson){

    suspend fun searchMulti(
        query: String,
        page: Int = Constants.DEFAULT_PAGE
    ): CinemaxResponse<MultiResponse> {
        return safeApiCall{
            multiApiService.getSearchMulti(query, page)
        }
    }
}
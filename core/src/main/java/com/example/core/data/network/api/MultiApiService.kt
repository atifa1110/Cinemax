package com.example.core.data.network.api

import com.example.core.data.network.Constants
import com.example.core.data.network.response.MultiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MultiApiService {

    @GET(Constants.Path.SEARCH_MULTI)
    suspend fun getSearchMulti(
        @Query(Constants.Fields.QUERY) query: String = "",
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE,
        @Query("include_adult") adult: Boolean = true,
    ): Response<MultiResponse>

}

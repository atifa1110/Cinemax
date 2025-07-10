package com.example.core.data.network.response

import com.example.core.data.network.Constants
import com.example.core.data.network.model.tv.TvShowNetwork
import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    @SerializedName(Constants.Fields.PAGE)
    val page: Int,
    @SerializedName(Constants.Fields.RESULTS)
    val results: List<TvShowNetwork>?,
    @SerializedName(Constants.Fields.TOTAL_PAGES)
    val totalPages: Int,
    @SerializedName(Constants.Fields.TOTAL_RESULTS)
    val totalResults: Int,
)
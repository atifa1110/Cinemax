package com.example.core.data.network.response

import com.example.core.data.network.Constants
import com.example.core.data.network.model.multi.MultiNetwork
import com.google.gson.annotations.SerializedName

data class MultiResponse(
    @SerializedName(Constants.Fields.PAGE)
    val page: Int,
    @SerializedName(Constants.Fields.RESULTS)
    val results: List<MultiNetwork>,
    @SerializedName(Constants.Fields.TOTAL_PAGES)
    val totalPages: Int?=0,
    @SerializedName(Constants.Fields.TOTAL_RESULTS)
    val totalResults: Int?=0
)
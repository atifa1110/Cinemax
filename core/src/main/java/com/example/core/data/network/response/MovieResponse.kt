package com.example.core.data.network.response

import com.example.core.data.network.Constants
import com.example.core.data.network.model.movie.MovieNetwork
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName(Constants.Fields.PAGE)
    val page: Int,
    @SerializedName(Constants.Fields.RESULTS)
    val results: List<MovieNetwork>?,
    @SerializedName(Constants.Fields.TOTAL_PAGES)
    val totalPages: Int?=0,
    @SerializedName(Constants.Fields.TOTAL_RESULTS)
    val totalResults: Int?=0
)
package com.example.core.data.network.model.movie

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkProductionCompany(
    @SerializedName(Constants.Fields.ID)
    val id: Int,

    @SerializedName(Constants.Fields.NAME)
    val name: String,

    @SerializedName(Constants.Fields.LOGO_PATH)
    val logoPath: String?,

    @SerializedName(Constants.Fields.ORIGIN_COUNTRY)
    val originCountry: String
)

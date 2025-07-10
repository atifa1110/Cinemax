package com.example.core.data.network.model.tv

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkOrganization(
    @SerializedName(Constants.Fields.ID)
    val id: Int,

    @SerializedName(Constants.Fields.NAME)
    val name: String,

    @SerializedName(Constants.Fields.ORIGIN_COUNTRY)
    val originCountry: String,

    @SerializedName(Constants.Fields.LOGO_PATH)
    val logoPath: String?
)
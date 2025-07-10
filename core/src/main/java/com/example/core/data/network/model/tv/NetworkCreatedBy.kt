package com.example.core.data.network.model.tv

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkCreatedBy(
    @SerializedName(Constants.Fields.ID)
    val id: Int,

    @SerializedName(Constants.Fields.CREDIT_ID)
    val creditId: String,

    @SerializedName(Constants.Fields.NAME)
    val name: String,

    @SerializedName(Constants.Fields.GENDER)
    val gender: Int,

    @SerializedName(Constants.Fields.PROFILE_PATH)
    val profilePath: String?
)

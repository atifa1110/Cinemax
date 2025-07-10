package com.example.core.data.local.model.detailMovie

import com.example.core.data.local.util.Constants
import com.google.gson.annotations.SerializedName

data class CastEntity(
    @SerializedName(Constants.Fields.ID)
    val id: Int,

    @SerializedName(Constants.Fields.NAME)
    val name: String,

    @SerializedName(Constants.Fields.ADULT)
    val adult: Boolean,

    @SerializedName(Constants.Fields.CAST_ID)
    val castId: Int?,

    @SerializedName(Constants.Fields.CHARACTER)
    val character: String,

    @SerializedName(Constants.Fields.CREDIT_ID)
    val creditId: String,

    @SerializedName(Constants.Fields.GENDER)
    val gender: Int?,

    @SerializedName(Constants.Fields.KNOWN_FOR_DEPARTMENT)
    val knownForDepartment: String,

    @SerializedName(Constants.Fields.ORDER)
    val order: Int,

    @SerializedName(Constants.Fields.ORIGINAL_NAME)
    val originalName: String,

    @SerializedName(Constants.Fields.POPULARITY)
    val popularity: Double,

    @SerializedName(Constants.Fields.PROFILE_PATH)
    val profilePath: String?
)
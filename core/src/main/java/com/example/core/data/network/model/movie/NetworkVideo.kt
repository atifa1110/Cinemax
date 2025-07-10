package com.example.core.data.network.model.movie

import com.example.core.data.network.Constants
import com.google.gson.annotations.SerializedName

data class NetworkVideo(
    @SerializedName(Constants.Fields.ID)
    val id: String,
    @SerializedName(Constants.Fields.ISO6391)
    val language: String,
    @SerializedName(Constants.Fields.ISO31661)
    val country: String,
    @SerializedName(Constants.Fields.KEY)
    val key: String, // Video key used for URLs (e.g., YouTube)
    @SerializedName(Constants.Fields.NAME)
    val name: String, // Name of the video (e.g., "Trailer")
    @SerializedName(Constants.Fields.SITE)
    val site: String, // Site hosting the video (e.g., "YouTube")
    @SerializedName(Constants.Fields.SIZE)
    val size: Int, // Resolution size (e.g., 1080)
    @SerializedName(Constants.Fields.TYPE)
    val type: String, // Type of video (e.g., "Trailer", "Teaser")
    @SerializedName(Constants.Fields.OFFICIAL)
    val official: Boolean, // Whether it's an official video
    @SerializedName(Constants.Fields.PUBLISHED)
    val publishedAt: String // Publication date
)
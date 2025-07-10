package com.example.core.data.local.converter

import androidx.room.TypeConverter
import com.example.core.data.local.model.detailMovie.VideosListEntity
import com.google.gson.Gson

internal class VideosConverter {

    @TypeConverter
    fun fromVideos(videos : VideosListEntity): String {
        return Gson().toJson(videos)
    }

    @TypeConverter
    fun toVideos(json: String): VideosListEntity {
        return Gson().fromJson(json, VideosListEntity::class.java)
    }
}
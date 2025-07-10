package com.example.core.data.local.converter

import androidx.room.TypeConverter
import com.example.core.data.local.model.detailMovie.ImagesListEntity
import com.google.gson.Gson

internal class ImagesConverter {

    @TypeConverter
    fun fromImages(images: ImagesListEntity): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun toImages(json: String): ImagesListEntity {
        return Gson().fromJson(json, ImagesListEntity::class.java)
    }
}
package com.example.core.data.local.converter

import androidx.room.TypeConverter
import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.google.gson.Gson

internal class CreditsConverter {

    @TypeConverter
    fun fromCredits(credits: CreditsListEntity): String {
        return Gson().toJson(credits)
    }

    @TypeConverter
    fun toCredits(json: String): CreditsListEntity {
        return Gson().fromJson(json, CreditsListEntity::class.java)
    }
}
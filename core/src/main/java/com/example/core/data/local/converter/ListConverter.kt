package com.example.core.data.local.converter

import androidx.room.TypeConverter
import com.example.core.data.local.model.movie.Genre
import com.example.core.data.local.model.movie.GenreEntity
import com.example.core.data.local.model.tv.SeasonEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class ListConverter {

    @TypeConverter
    fun fromGenreStringList(list: List<String>?): String? {
        return if (list == null) null else Gson().toJson(list)
    }

    @TypeConverter
    fun toGenreStringList(list: String?): List<String>? {
        if (list == null) return null
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(list, listType)
    }

    @TypeConverter
    fun fromGenreIntList(genreEntities: List<Int>?): String? {
        return if (genreEntities == null) null else Gson().toJson(genreEntities)
    }

    @TypeConverter
    fun toGenreIntList(genresString: String?): List<Int>? {
        if (genresString == null) return null
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(genresString, listType)
    }

    @TypeConverter
    fun fromGenreList(genreEntities: List<Genre>?): String? {
        return if (genreEntities == null) null else Gson().toJson(genreEntities)
    }

    @TypeConverter
    fun toGenreList(genresString: String?): List<Genre>? {
        if (genresString == null) return null
        val listType = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(genresString, listType)
    }

    @TypeConverter
    fun fromGenreEntityList(genreEntities: List<GenreEntity>?): String? {
        return if (genreEntities == null) null else Gson().toJson(genreEntities)
    }

    @TypeConverter
    fun toGenreEntityList(genresString: String?): List<GenreEntity>? {
        if (genresString == null) return null
        val listType = object : TypeToken<List<GenreEntity>>() {}.type
        return Gson().fromJson(genresString, listType)
    }

    @TypeConverter
    fun fromSeasonEntityList(seasons: List<SeasonEntity>?): String? {
        return if (seasons == null) null else Gson().toJson(seasons)
    }

    @TypeConverter
    fun toSeasonEntityList(season: String?): List<SeasonEntity>? {
        if (season == null) return null
        val listType = object : TypeToken<List<SeasonEntity>>() {}.type
        return Gson().fromJson(season, listType)
    }
}
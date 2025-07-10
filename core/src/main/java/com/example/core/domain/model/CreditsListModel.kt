package com.example.core.domain.model

data class CreditsListModel(
    val cast: List<CastModel>,
    val crew: List<CrewModel>
)

data class CrewModel(
    val id: Int,
    val adult: Boolean,
    val creditId: String,
    val department: String,
    val gender: Int?,
    val job: String,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?
)

data class CastModel(
    val id: Int,
    val name: String,
    val adult: Boolean,
    val castId: Int?,
    val character: String,
    val creditId: String,
    val gender: Int?,
    val knownForDepartment: String,
    val order: Int,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?
)
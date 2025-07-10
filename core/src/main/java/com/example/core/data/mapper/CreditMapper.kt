package com.example.core.data.mapper

import com.example.core.data.local.model.detailMovie.CastEntity
import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.local.model.detailMovie.CrewEntity
import com.example.core.data.network.asImageURL
import com.example.core.data.network.model.movie.NetworkCast
import com.example.core.data.network.model.movie.NetworkCrew
import com.example.core.data.network.model.movie.NetworkListCredits
import com.example.core.domain.model.CastModel
import com.example.core.domain.model.CreditsListModel
import com.example.core.domain.model.CrewModel

fun NetworkListCredits.asCredits() = CreditsListEntity(
    cast = cast.map(NetworkCast::asCast),
    crew = crew.map(NetworkCrew::asCrew)
)

fun NetworkCast.asCast() = CastEntity(
    id = id,
    name = name,
    adult = adult,
    castId = castId,
    character = character,
    creditId = creditId,
    gender = gender,
    knownForDepartment = knownForDepartment,
    order = order,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath?.asImageURL()
)

fun NetworkCrew.asCrew() = CrewEntity(
    id = id,
    adult = adult,
    creditId = creditId,
    department = department,
    gender = gender,
    job = job,
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath?.asImageURL()
)

fun CreditsListEntity.asCreditsModel() = CreditsListModel(
    cast = cast.map(CastEntity::asCastModel),
    crew = crew.map(CrewEntity::asCrewModel)
)

fun CastEntity.asCastModel() = CastModel(
    id = id,
    name = name,
    adult = adult,
    castId = castId,
    character = character,
    creditId = creditId,
    gender = gender,
    knownForDepartment = knownForDepartment,
    order = order,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath
)

fun CrewEntity.asCrewModel() = CrewModel(
    id = id,
    adult = adult,
    creditId = creditId,
    department = department,
    gender = gender,
    job = job,
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath
)
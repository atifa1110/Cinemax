package com.example.core.ui.mapper

import com.example.core.domain.model.CastModel
import com.example.core.domain.model.CreditsListModel
import com.example.core.domain.model.CrewModel
import com.example.core.ui.model.Cast
import com.example.core.ui.model.CreditsList
import com.example.core.ui.model.Crew


internal fun CreditsListModel.asCredits() = CreditsList(
    cast = cast.map(CastModel::asCast),
    crew = crew.map(CrewModel::asCrew)
)

private fun CastModel.asCast() = Cast(
    id = id,
    name = name,
    character = character,
    profilePath = profilePath
)

private fun CrewModel.asCrew() = Crew(
    id = id,
    name = name,
    job = job,
    profilePath = profilePath
)
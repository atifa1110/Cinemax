package com.example.core.ui.mapper

import com.example.core.R
import com.example.core.domain.model.GenreModel
import com.example.core.ui.model.Genre

fun List<GenreModel>.asGenres() = map { genre ->
    Genre(nameResourceId = genre.asNameResourceId())
}

internal fun List<Genre>.asGenreModel() = map(Genre::asGenreModel)

private fun Genre.asGenreModel() = genresNameResourcesGenre.getValue(this.nameResourceId)
private fun GenreModel.asNameResourceId() = genresNameResources.getValue(this)

private val genresNameResourcesGenre = mapOf(
    R.string.action to GenreModel.ACTION ,
    R.string.adventure to GenreModel.ADVENTURE ,
    R.string.action_adventure to GenreModel.ACTION_ADVENTURE,
    R.string.animation to GenreModel.ANIMATION,
    R.string.comedy to GenreModel.COMEDY,
    R.string.crime to GenreModel.CRIME,
    R.string.documentary to GenreModel.DOCUMENTARY,
    R.string.drama to GenreModel.DRAMA,
    R.string.family to GenreModel.FAMILY,
    R.string.fantasy to GenreModel.FANTASY,
    R.string.history to GenreModel.HISTORY,
    R.string.horror to GenreModel.HORROR,
    R.string.kids to GenreModel.KIDS,
    R.string.music to GenreModel.MUSIC,
    R.string.mystery to GenreModel.MYSTERY,
    R.string.news to GenreModel.NEWS,
    R.string.reality to GenreModel.REALITY,
    R.string.romance to GenreModel.ROMANCE,
    R.string.science_fiction to GenreModel.SCIENCE_FICTION,
    R.string.science_fiction_fantasy to GenreModel.SCIENCE_FICTION_FANTASY,
    R.string.soap to GenreModel.SOAP,
    R.string.talk to GenreModel.TALK,
    R.string.thriller to GenreModel.THRILLER,
    R.string.tv_movie to GenreModel.TV_MOVIE,
    R.string.war to GenreModel.WAR,
    R.string.war_politics to GenreModel.WAR_POLITICS,
    R.string.western to GenreModel.WESTERN
)

private val genresNameResources = mapOf(
    GenreModel.ACTION to R.string.action,
    GenreModel.ADVENTURE to R.string.adventure,
    GenreModel.ACTION_ADVENTURE to R.string.action_adventure,
    GenreModel.ANIMATION to R.string.animation,
    GenreModel.COMEDY to R.string.comedy,
    GenreModel.CRIME to R.string.crime,
    GenreModel.DOCUMENTARY to R.string.documentary,
    GenreModel.DRAMA to R.string.drama,
    GenreModel.FAMILY to R.string.family,
    GenreModel.FANTASY to R.string.fantasy,
    GenreModel.HISTORY to R.string.history,
    GenreModel.HORROR to R.string.horror,
    GenreModel.KIDS to R.string.kids,
    GenreModel.MUSIC to R.string.music,
    GenreModel.MYSTERY to R.string.mystery,
    GenreModel.NEWS to R.string.news,
    GenreModel.REALITY to R.string.reality,
    GenreModel.ROMANCE to R.string.romance,
    GenreModel.SCIENCE_FICTION to R.string.science_fiction,
    GenreModel.SCIENCE_FICTION_FANTASY to R.string.science_fiction_fantasy,
    GenreModel.SOAP to R.string.soap,
    GenreModel.TALK to R.string.talk,
    GenreModel.THRILLER to R.string.thriller,
    GenreModel.TV_MOVIE to R.string.tv_movie,
    GenreModel.WAR to R.string.war,
    GenreModel.WAR_POLITICS to R.string.war_politics,
    GenreModel.WESTERN to R.string.western
)

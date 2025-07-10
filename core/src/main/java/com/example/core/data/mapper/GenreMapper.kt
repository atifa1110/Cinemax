package com.example.core.data.mapper

import com.example.core.data.local.model.movie.Genre
import com.example.core.data.local.model.movie.GenreEntity
import com.example.core.data.network.model.genre.GenreNetwork
import com.example.core.data.network.model.genre.GenreNetworkWithId
import com.example.core.domain.model.GenreModel

@JvmName("intListAsGenreModels")
internal fun List<Int>.asGenreModels() = map(Int::asGenreModel)
private fun Int.asGenreModel() = GenreModel[GenreNetworkWithId[this].genreName]

internal fun GenreNetwork.asGenre() = id.asGenre()
internal fun List<GenreNetwork>.asGenreNetwork() = map(GenreNetwork::asGenre)
private fun Int.asGenre() = Genre[GenreNetworkWithId[this].genreName]

internal fun Genre.asGenreModel() = GenreModel[genreName]
internal fun List<Genre>.asGenreModels() = map(Genre::asGenreModel)

internal fun List<GenreEntity>.asGenreModel() = map(GenreEntity::asGenreModel)
internal fun GenreEntity.asGenreModel() = name.asGenreModel()
private fun String.asGenreModel() = GenreModel[this]

internal fun List<GenreModel>.asGenreEntity() = map(GenreModel::asGenreEntity)
internal fun GenreModel.asGenreEntity() = GenreEntity(
    name = genreName
)

internal fun List<Int>.asGenres() = map(Int::asGenre)
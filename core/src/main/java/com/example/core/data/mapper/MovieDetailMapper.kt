package com.example.core.data.mapper

import com.example.core.data.local.model.detailMovie.CreditsListEntity
import com.example.core.data.local.model.detailMovie.ImagesListEntity
import com.example.core.data.local.model.detailMovie.MovieDetailsEntity
import com.example.core.data.local.model.detailMovie.VideosListEntity
import com.example.core.data.network.asImageURL
import com.example.core.data.network.getFormatReleaseDate
import com.example.core.data.network.getRating
import com.example.core.data.network.model.movie.MovieDetailNetwork
import com.example.core.domain.model.MovieDetailModel

fun MovieDetailNetwork.asMovieDetailsEntity() = MovieDetailsEntity(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    budget = budget?:0,
    genreEntities = genres?.asGenreNetwork()?: listOf(),
    homepage = homepage,
    imdbId = imdbId,
    originalLanguage = originalLanguage?:"",
    originalTitle = originalTitle?:"",
    overview = overview?:"",
    popularity = popularity?:0.0,
    posterPath = posterPath?.asImageURL(),
    releaseDate = releaseDate?.getFormatReleaseDate(),
    revenue = revenue?:0,
    runtime = runtime,
    status = status?:"",
    tagline = tagline,
    title = title?:"",
    video = video?:false,
    voteAverage = voteAverage?:0.0,
    voteCount = voteCount?:0,
    rating =  voteAverage?.getRating()?:0.0,
    credits = credits?.asCredits()?: CreditsListEntity(listOf(), listOf()),
    videos = videos?.asVideoEntity()?: VideosListEntity(listOf()),
    images = images?.asImagesEntity()?: ImagesListEntity(listOf(), listOf())
)

fun MovieDetailsEntity.asMovieDetailsModel(isWishListed: Boolean) = MovieDetailModel(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    budget = budget,
    genres = genreEntities.asGenreModels(),
    homepage = homepage,
    imdbId = imdbId,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    status = status,
    tagline = tagline,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    rating = rating,
    credits = credits.asCreditsModel(),
    images = images.asImagesModel(),
    videos = videos.asVideoModel(),
    isWishListed = isWishListed
)
package com.example.core.domain.usecase

import com.example.core.domain.model.CreditsListModel
import com.example.core.domain.model.GenreModel
import com.example.core.domain.model.ImagesListModel
import com.example.core.domain.model.MovieDetailModel
import com.example.core.domain.model.VideosListModel
import com.example.core.domain.repository.wishlist.WishListRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddMovieToWishlistUseCaseTest {

    private lateinit var repository: WishListRepository
    private lateinit var useCase: AddMovieToWishlistUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = AddMovieToWishlistUseCase(repository)
    }

    @Test
    fun `invoke calls addMovieToWishlist on repository`() = runTest {
        val movie = MovieDetailModel(
            id = 671,
            adult = false,
            backdropPath = "/5jkE2SzR5uR2egEb1rRhF22JyWN.jpg",
            budget = 125000000,
            genres = listOf(GenreModel.ACTION),
            homepage = "https://www.warnerbros.com/movies/harry-potter-and-sorcerers-stone/",
            imdbId = "tt0241527",
            originalLanguage = "en",
            originalTitle = "Harry Potter and the Philosopher's Stone",
            overview = "",
            popularity = 239.401,
            posterPath = "/wuMc08IPKEatf9rnMNXvIDxqP4W.jpg",
            releaseDate = "2001-11-16",
            revenue = 976475550L,
            runtime = 152,
            status = "Released",
            tagline = "Let the magic begin.",
            title = "Harry Potter and the Philosopher's Stone",
            video = false,
            voteAverage = 7.907,
            voteCount = 27620,
            credits = CreditsListModel(emptyList(), emptyList()),
            images = ImagesListModel(emptyList(), emptyList()),
            videos = VideosListModel(emptyList()),
            rating = 0.0,
            isWishListed = false
        )

        useCase(movie)

        coVerify(exactly = 1) { repository.addMovieToWishlist(movie) }
    }
}

package com.example.core.domain.usecase

import com.example.core.domain.model.CreditsListModel
import com.example.core.domain.model.TvShowDetailModel
import com.example.core.domain.repository.wishlist.WishListRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddTvShowToWishlistUseCaseTest {

    private lateinit var repository: WishListRepository
    private lateinit var useCase: AddTvShowToWishlistUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = AddTvShowToWishlistUseCase(repository)
    }

    @Test
    fun `invoke should call addTvShowToWishlist on repository`() = runTest {
        val tvShow = TvShowDetailModel(
            id = 93405,
            name = "Squid Game",
            adult = false,
            backdropPath = "/ukAmSyNdtWduHZfm27R2EOsguKt.jpg",
            credits = CreditsListModel(emptyList(), emptyList()),
            episodeRunTime = emptyList(),
            firstAirDate = "2021-09-17",
            genres = emptyList(),
            homepage = "https://www.netflix.com/title/81040344",
            inProduction= true,
            languages = emptyList(),
            lastAirDate= "2024-12-26",
            numberOfEpisodes = 16,
            numberOfSeasons = 3,
            originCountry = emptyList(),
            originalLanguage = "ko",
            originalName = "오징어 게임",
            overview = "",
            popularity = 12404.255,
            posterPath = "/dDlEmu3EZ0Pgg93K2SVNLCjCSvE.jpg",
            seasons = emptyList(),
            status = "Returning Series",
            tagline = "45.6 billion won is child's play.",
            type = "Scripted",
            voteAverage = 7.8,
            voteCount = 14677,
            rating = 0.0,
            isWishListed = false
        )

        useCase(tvShow)

        coVerify(exactly = 1) { repository.addTvShowToWishlist(tvShow) }
    }
}

package com.example.core.data.network.datasource

import androidx.paging.PagingSource
import com.example.core.data.network.Constants
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.model.multi.MultiNetwork
import com.example.core.data.network.response.MultiResponse
import com.example.core.data.network.model.movie.MovieDetailNetwork
import com.example.core.data.network.model.movie.NetworkListCredits
import com.example.core.data.network.model.movie.NetworkListImages
import com.example.core.data.network.model.movie.NetworkListVideos
import com.example.core.data.network.model.tv.TvShowDetailNetwork
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SearchPagingSourceTest {

    private val movieDataSource: MovieNetworkDataSource = mockk()
    private val tvDataSource: TvShowNetworkDataSource = mockk()
    private val multiDataSource: MultiNetworkDataSource = mockk()

    private lateinit var pagingSource: SearchPagingSource

    @Before
    fun setup() {
        pagingSource = SearchPagingSource(
            query = "Harry",
            movieNetworkDataSource = movieDataSource,
            tvShowNetworkDataSource = tvDataSource,
            multiDataSource = multiDataSource
        )
    }

    val movieDetail = MovieDetailNetwork(
        id = 1, adult = false, backdropPath = "", budget = 0,
        genres = listOf(), homepage = "", imdbId = "tt0241527", originalLanguage = "",
        originalTitle =  "Harry Potter and the Philosopher's Stone", overview = "",
        popularity = 0.0, posterPath = "", releaseDate = "", revenue = 0,
        runtime = 0, status = "", tagline = "",
        title =  "Harry Potter and the Philosopher's Stone",
        video = false, voteAverage = 2.0, voteCount = 0,
        credits = NetworkListCredits(listOf(), listOf()),
        images = NetworkListImages(listOf(),listOf()),
        videos = NetworkListVideos(listOf())
    )

    val tvDetail = TvShowDetailNetwork(
        id = 1,
        name = "The Witcher",
        adult = false,
        numberOfSeasons = 2,
        backdropPath = "/ukAmSyNdtWduHZfm27R2EOsguKt.jpg",
        createdBy = emptyList(),
        credits = NetworkListCredits(emptyList(), emptyList()),
        episodeRunTime = emptyList(),
        firstAirDate = "2021-09-17",
        genres = emptyList(),
        voteAverage = 7.8,
        voteCount = 14677,
    )
    @Test
    fun `load returns page when response is success`() = runTest {
        // Given
        val movieItem = MultiNetwork(id = 1, mediaType = "movie", title = "Harry Potter")
        val tvItem = MultiNetwork(id = 2, mediaType = "tv", name = "The Witcher")

        coEvery { multiDataSource.searchMulti(eq("Harry"), eq(Constants.DEFAULT_PAGE))
        } returns CinemaxResponse.success(MultiResponse(1, listOf(movieItem, tvItem), 1, 1))

        coEvery { movieDataSource.getDetailMovie(1) } returns CinemaxResponse.success(movieDetail)
        coEvery { tvDataSource.getDetailTv(eq(2), eq(Constants.Fields.DETAILS_APPEND_TO_RESPONSE_TV))
        } returns CinemaxResponse.success(tvDetail)

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = 1, loadSize = 2, placeholdersEnabled = false)
        )

        // Then
        assertTrue(result is PagingSource.LoadResult.Page)
        val data = (result as PagingSource.LoadResult.Page).data
        assertEquals(2, data.size)
    }

    @Test
    fun `load returns empty when no media found`() = runTest {
        coEvery {
            multiDataSource.searchMulti(eq("Harry"), eq(Constants.DEFAULT_PAGE))
        } returns CinemaxResponse.success(MultiResponse(1, emptyList(), 1, 1))

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = 1, loadSize = 10, placeholdersEnabled = false)
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        assertTrue((result as PagingSource.LoadResult.Page).data.isEmpty())
    }

    @Test
    fun `load returns error when failure response`() = runTest {
        coEvery {
            multiDataSource.searchMulti(eq("Harry"), eq(Constants.DEFAULT_PAGE))
        } returns CinemaxResponse.failure(404, "Not Found")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = 1, loadSize = 10, placeholdersEnabled = false)
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals("Not Found",(result as PagingSource.LoadResult.Error).throwable.message)
    }

    @Test
    fun `load returns error on exception`() = runTest {
        coEvery {
            multiDataSource.searchMulti(eq("Harry"), eq(Constants.DEFAULT_PAGE))
        } returns CinemaxResponse.failure(code = 408, error = "Timeout")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = 1, loadSize = 10, placeholdersEnabled = false)
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals("Timeout",(result as PagingSource.LoadResult.Error).throwable.message)
    }
}

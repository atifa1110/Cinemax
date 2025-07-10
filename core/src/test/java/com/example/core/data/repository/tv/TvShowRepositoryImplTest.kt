package com.example.core.data.repository.tv

import androidx.paging.testing.asSnapshot
import com.example.core.data.local.model.movie.Genre
import com.example.core.data.local.model.tv.TvShowEntity
import com.example.core.data.local.source.TvShowDatabaseSource
import com.example.core.data.local.util.DatabaseMediaType
import com.example.core.data.mapper.asMediaType
import com.example.core.data.mapper.asNetworkMediaType
import com.example.core.data.network.PAGE_SIZE
import com.example.core.data.network.datasource.TvShowNetworkDataSource
import com.example.core.data.network.model.tv.TvShowDetailNetwork
import com.example.core.data.network.model.tv.TvShowNetwork
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.response.TvShowResponse
import com.example.core.data.repository.utils.MainDispatcherRule
import com.example.core.data.repository.utils.FakeTvPagingSource
import com.example.core.domain.model.GenreModel
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.TvShowModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue


@OptIn(ExperimentalCoroutinesApi::class)
class TvShowRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: TvShowRepositoryImpl

    private val databaseDataSource: TvShowDatabaseSource = mockk()
    private val tvShowNetworkDataSource: TvShowNetworkDataSource = mockk()

    @Before
    fun setup() {
        repository = TvShowRepositoryImpl(
            databaseDataSource,
            tvShowNetworkDataSource,
        )
    }

    fun createMockTvShowNetworkModel(id: Int): TvShowNetwork {
        return TvShowNetwork(
            id = id,
            name = "Tv Show $id",
            overview = "",
            popularity = 0.0,
            firstAirDate = "2023-10-26",
            genreIds = emptyList(),
            originalName = "",
            originalLanguage = "",
            originCountry = emptyList(),
            voteAverage = 8.0,
            voteCount = 0,
            posterPath = "",
            backdropPath = ""
        )
    }

    fun createMockTvShowEntity(id: Int, mediaType: DatabaseMediaType.TvShow): TvShowEntity {
        return TvShowEntity(
            id = id,
            mediaType = mediaType,
            networkId = id,
            name = "Tv Show $id",
            overview = "",
            popularity = 7.0,
            firstAirDate = "October 26, 2023",
            genres = listOf(Genre.ACTION),
            originalName = "",
            originalLanguage = "",
            originCountry = emptyList(),
            voteAverage = 8.0,
            voteCount = 0,
            posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
            seasons = 0,
            rating = 0.0
        )
    }

    fun createMockDetailTvModel(id: Int): TvShowDetailNetwork {
        return TvShowDetailNetwork(
            id = id,
            adult = false,
            name = "Tv Show $id",
            overview = "",
            posterPath = "/poster.jpg",
            firstAirDate = "2023-10-26",
            voteAverage = 8.0,
            numberOfSeasons = 4
        )
    }


    @Test
    fun `getByMediaType returns cached movies data and then fetches and updates`() = runTest {
        val mediaTypeModel = MediaTypeModel.TvShow.Popular
        val mediaType = mediaTypeModel.asMediaType()
        val networkType = mediaType.asNetworkMediaType()
        val tvsEntity = listOf(createMockTvShowEntity(1,mediaType), createMockTvShowEntity(2,mediaType))
        val tvsModel = listOf(TvShowModel(
            id = 1, name = "Tv Show 1", overview = "",voteAverage = 8.0, rating = 0.0,
            genres= listOf(GenreModel.ACTION), posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg", firstAirDate = "October 26, 2023" ,
            originalName= "",originalLanguage = "", originCountry=emptyList(),voteCount = 0,
            popularity = 7.0, seasons = 0
        ), TvShowModel(
            id = 2, name = "Tv Show 2", overview = "",voteAverage = 8.0, rating = 0.0,
            genres= listOf(GenreModel.ACTION), posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg", firstAirDate = "October 26, 2023" ,
            originalName= "",originalLanguage = "", originCountry= emptyList(),voteCount = 0,
            popularity = 7.0, seasons = 0)
        )
        val networkTv = listOf(createMockTvShowNetworkModel(1), createMockTvShowNetworkModel(2))
        val movieResponse = TvShowResponse(1,networkTv,2,5)

        // Arrange (Cached data exists)
        coEvery { databaseDataSource.getByMediaType(mediaType, PAGE_SIZE) } returns flowOf(emptyList())
        coEvery { tvShowNetworkDataSource.getByMediaType(networkType) } returns CinemaxResponse.Success(movieResponse)
        coEvery { databaseDataSource.deleteByMediaTypeAndInsertAll(mediaType, any()) } returns Unit
        coEvery { databaseDataSource.getByMediaType(mediaType, PAGE_SIZE) } returns flowOf(tvsEntity)

        // Act
        val results = repository.getByMediaType(mediaTypeModel).toList()

        // Assert (Initial cached data)
        assertTrue(results[0] is CinemaxResponse.Loading)
        assertTrue(results[1] is CinemaxResponse.Loading)
        assertTrue(results[2] is CinemaxResponse.Success)

        val successResult = results[2] as CinemaxResponse.Success
        assertEquals(tvsModel, successResult.value)

        coVerify(atLeast = 1, atMost = 2) { databaseDataSource.getByMediaType(mediaType, PAGE_SIZE) }
        coVerify(exactly = 1) { tvShowNetworkDataSource.getByMediaType(any()) }
        coVerify { databaseDataSource.deleteByMediaTypeAndInsertAll(mediaType, any()) }
    }

    @Test
    fun `getByMediaType returns network failure error with no cache`() = runTest {
        // Given
        val mediaTypeModel = MediaTypeModel.TvShow.Popular
        val mediaType = mediaTypeModel.asMediaType()
        val networkType = mediaType.asNetworkMediaType()
        val errorMessage = "Network Error"

        // Arrange (No cached data, network fetch fails)
        coEvery { databaseDataSource.getByMediaType(mediaType, PAGE_SIZE) } returns flowOf(emptyList())
        coEvery { tvShowNetworkDataSource.getByMediaType(networkType) } returns CinemaxResponse.Failure(400,errorMessage)

        // Act
        val results = repository.getByMediaType(mediaTypeModel).toList()

        // Assert (Only error)
        assertTrue(results[0] is CinemaxResponse.Loading)
        assertTrue(results[1] is CinemaxResponse.Loading)
        assertTrue(results[2] is CinemaxResponse.Failure)

        val failureResult = results[2] as CinemaxResponse.Failure
        assertEquals(errorMessage, failureResult.error)

        coVerify(atLeast = 1, atMost = 2) { databaseDataSource.getByMediaType(mediaType, PAGE_SIZE) }
        coVerify(exactly = 1) { tvShowNetworkDataSource.getByMediaType(any()) }
    }

    @Test
    fun `getPagingByMediaType returns success tv paging source data`() = runTest {
        val mediaTypeModel = MediaTypeModel.TvShow.Popular
        val mediaType = mediaTypeModel.asMediaType()
        val networkType = mediaType.asNetworkMediaType()
        // Arrange (Database has initial data)
        val tvsEntity = listOf(createMockTvShowEntity(1, mediaType),
            createMockTvShowEntity(2, mediaType))

        val tvsModel = listOf(TvShowModel(
            id = 1, name = "Tv Show 1", overview = "",voteAverage = 8.0, rating = 0.0,
            genres= listOf(GenreModel.ACTION), posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg", firstAirDate = "October 26, 2023" ,
            originalName= "",originalLanguage = "", originCountry=emptyList(),voteCount = 0,
            popularity = 7.0, seasons = 0
        ), TvShowModel(
            id = 2, name = "Tv Show 2", overview = "",voteAverage = 8.0, rating = 0.0,
            genres= listOf(GenreModel.ACTION), posterPath = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w500/backdrop.jpg", firstAirDate = "October 26, 2023" ,
            originalName= "",originalLanguage = "", originCountry= emptyList(),voteCount = 0,
            popularity = 7.0, seasons = 0)
        )

        val tv1 = createMockTvShowNetworkModel(1)
        val tv2 = createMockTvShowNetworkModel(2)
        val mockResponse = TvShowResponse(1,listOf(tv1, tv2),2,5)
        val tvDetail1 = createMockDetailTvModel(1)
        val tvDetail2 = createMockDetailTvModel(2)

        coEvery { tvShowNetworkDataSource.getByMediaType(networkType, 1) } returns CinemaxResponse.Success(mockResponse)
        coEvery { tvShowNetworkDataSource.getDetailTv(1) } returns CinemaxResponse.Success(tvDetail1)
        coEvery { tvShowNetworkDataSource.getDetailTv(2) } returns CinemaxResponse.Success(tvDetail2)
        coEvery { databaseDataSource.handlePaging(any(), any(), any(), any()) } returns Unit
        coEvery { databaseDataSource.updateTvSeasons(1, 4) } just Runs
        coEvery { databaseDataSource.updateTvSeasons(2, 4) } just Runs
        coEvery { databaseDataSource.getPagingByMediaType(mediaType) } returns FakeTvPagingSource(tvsEntity)
        coEvery { databaseDataSource.getRemoteKeyByIdAndMediaType(any(), eq(mediaType)) } returns null

        // Act
        val pagingDataFlow = repository.getPagingByMediaType(mediaTypeModel)
        val result = pagingDataFlow.asSnapshot()

        // Assert
        assertEquals(2, result.size)
        assertEquals(tvsModel, result)

        coVerify { tvShowNetworkDataSource.getByMediaType(networkType, 1) }
        coVerify { tvShowNetworkDataSource.getDetailTv(1) }
        coVerify { tvShowNetworkDataSource.getDetailTv(2) }
        coVerify { databaseDataSource.updateTvSeasons(1, 4) }
        coVerify { databaseDataSource.updateTvSeasons(2, 4) }
        coVerify { databaseDataSource.getPagingByMediaType(mediaType) }
    }

    @Test
    fun `getPagingByMediaType returns network error`() = runTest {
        val mediaTypeModel = MediaTypeModel.TvShow.Popular
        val mediaType = mediaTypeModel.asMediaType()
        val networkType = mediaType.asNetworkMediaType()
        val errorMessage = "Network Error"

        coEvery { tvShowNetworkDataSource.getByMediaType(networkType, 1) } returns CinemaxResponse.Failure(400,errorMessage)
        coEvery { databaseDataSource.getPagingByMediaType(mediaType) } returns FakeTvPagingSource(emptyList())
        coEvery { databaseDataSource.getRemoteKeyByIdAndMediaType(any(), eq(mediaType)) } returns null

        val pagingDataFlow = repository.getPagingByMediaType(mediaTypeModel)

        val exception = assertFailsWith<Exception> {
            pagingDataFlow.asSnapshot()
        }

        assertEquals(errorMessage, exception.message)
    }

}
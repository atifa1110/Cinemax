package com.example.core.domain.usecase

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.TvShowModel
import com.example.core.domain.repository.tv.TvShowRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetTvPagingUseCaseTest {

    private lateinit var repository: TvShowRepository
    private lateinit var useCase: GetTvPagingUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        useCase = GetTvPagingUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should return TvShowModel paging data`() = runTest {
        // Given
        val tvShowList = listOf(
            TvShowModel(
                id = 1, name = "Breaking Bad", overview = "Chemistry teacher turns...",
                popularity = 10.0, genres = emptyList(), originalLanguage = "en",
                voteAverage = 7.5, voteCount = 100, posterPath = "", backdropPath = "",
                rating = 3.5, firstAirDate = "", originalName = "", originCountry = emptyList(),
                seasons = 2),
            TvShowModel(id = 2, name = "The Office", overview = "Dunder Mifflin employees",
                popularity = 10.0, genres = emptyList(), originalLanguage = "en",
                voteAverage = 7.5, voteCount = 100, posterPath = "", backdropPath = "",
                rating = 3.5, firstAirDate = "", originalName = "", originCountry = emptyList(),
                seasons = 2)
        )

        val pagingData = PagingData.from(tvShowList)
        coEvery { repository.getPagingByMediaType(any()) } returns flowOf(pagingData)

        // When
        val result = useCase(MediaTypeModel.TvShow.Popular)

        // Then: collect and inspect PagingData
        val differ = AsyncPagingDataDiffer(
            diffCallback = TvShowDiffCallback(),
            updateCallback = NoopListCallback(),
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher
        )

        result.collectLatest { differ.submitData(it) }

        advanceUntilIdle()

        assertEquals(2, differ.itemCount)
        assertEquals("Breaking Bad", differ.getItem(0)?.name)
        assertEquals("The Office", differ.getItem(1)?.name)
    }

    // DiffUtil.ItemCallback for TvShowModel
    class TvShowDiffCallback : DiffUtil.ItemCallback<TvShowModel>() {
        override fun areItemsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean =
            oldItem == newItem
    }

    // No-op ListUpdateCallback
    class NoopListCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}

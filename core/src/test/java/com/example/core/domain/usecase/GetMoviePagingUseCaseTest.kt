package com.example.core.domain.usecase

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.MovieModel
import com.example.core.domain.repository.movie.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class GetMoviePagingUseCaseTest {

    private val repository: MovieRepository = mockk()
    private lateinit var useCase: GetMoviePagingUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = GetMoviePagingUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit paging data when invoked`() = runTest {
        // Given
        val dummyMovie = MovieModel(
            id = 1,
            title = "Test Movie",
            overview = "Overview",
            popularity = 1.0,
            releaseDate = "2023-01-01",
            adult = false,
            genres = emptyList(),
            originalTitle = "Test Movie",
            originalLanguage = "en",
            voteAverage = 8.0,
            voteCount = 100,
            posterPath = "",
            backdropPath = "",
            video = false,
            rating = 4.0,
            mediaType = "movie",
            profilePath = "",
            runtime = 120
        )

        val pagingData = PagingData.from(listOf(dummyMovie))

        coEvery { repository.getPagingByMediaType(any()) } returns flowOf(pagingData)

        // When
        val result = useCase(MediaTypeModel.Movie.NowPlaying).first()

        // Then
        val differ = AsyncPagingDataDiffer(
            diffCallback = object : DiffUtil.ItemCallback<MovieModel>() {
                override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel) = oldItem.id == newItem.id
                override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel) = oldItem == newItem
            },
            updateCallback = NoopListCallback(),
            workerDispatcher = testDispatcher
        )

        differ.submitData(result)
        advanceUntilIdle()

        assertEquals(1, differ.snapshot().size)
        assertEquals("Test Movie", differ.snapshot()[0]?.title)
    }

    private class NoopListCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}

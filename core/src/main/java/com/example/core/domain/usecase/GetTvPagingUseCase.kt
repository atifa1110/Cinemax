package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.TvShowModel
import com.example.core.domain.repository.tv.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvPagingUseCase @Inject constructor(
    private val tvShowRepository : TvShowRepository
) {
    operator fun invoke(mediaTypeModel: MediaTypeModel.TvShow) : Flow<PagingData<TvShowModel>> {
        return tvShowRepository.getPagingByMediaType(mediaTypeModel)
    }
}
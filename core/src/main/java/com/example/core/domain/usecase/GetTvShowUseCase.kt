package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.TvShowModel
import com.example.core.domain.repository.tv.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvShowUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    operator fun invoke(mediaTypeModel: MediaTypeModel.TvShow): Flow<CinemaxResponse<List<TvShowModel>>> {
        return tvShowRepository.getByMediaType(mediaTypeModel)
    }
}
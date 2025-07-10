package com.example.core.domain.usecase

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.TvShowDetailModel
import com.example.core.domain.repository.tvdetail.TvShowDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailTvShowUseCase @Inject constructor(
    private val detailRepository : TvShowDetailRepository
) {
    operator fun invoke(id : Int): Flow<CinemaxResponse<TvShowDetailModel?>> {
        return detailRepository.getDetailTvShow(id)
    }
}
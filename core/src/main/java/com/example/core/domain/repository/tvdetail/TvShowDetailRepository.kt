package com.example.core.domain.repository.tvdetail

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.TvShowDetailModel
import kotlinx.coroutines.flow.Flow

interface TvShowDetailRepository {
    fun getDetailTvShow(id: Int) : Flow<CinemaxResponse<TvShowDetailModel?>>
}
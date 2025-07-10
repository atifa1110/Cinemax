package com.example.core.domain.repository.tv

import androidx.paging.PagingData
import com.example.core.data.network.response.CinemaxResponse
import com.example.core.domain.model.MediaTypeModel
import com.example.core.domain.model.TvShowModel
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

    fun getByMediaType(mediaTypeModel: MediaTypeModel.TvShow): Flow<CinemaxResponse<List<TvShowModel>>>
    fun getPagingByMediaType(mediaTypeModel: MediaTypeModel.TvShow): Flow<PagingData<TvShowModel>>
}
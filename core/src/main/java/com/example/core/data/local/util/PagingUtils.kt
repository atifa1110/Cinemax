package com.example.core.data.local.util

import androidx.paging.PagingConfig
import androidx.paging.PagingState
import com.example.core.data.network.PAGE_SIZE

internal object PagingUtils {
    internal suspend fun <T : Any, R> getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, T>,
        getRemoteKeyByEntity: suspend (T) -> R
    ): R? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.let { entity ->
            getRemoteKeyByEntity(entity)
        }
    }

    internal suspend fun <T : Any, R> getRemoteKeyForFirstItem(
        state: PagingState<Int, T>,
        getRemoteKeyByEntity: suspend (T) -> R
    ): R? = state.pages.firstOrNull {
        it.data.isNotEmpty()
    }?.data?.firstOrNull()?.let { entity ->
        getRemoteKeyByEntity(entity)
    }

    internal suspend fun <T : Any, R> getRemoteKeyForLastItem(
        state: PagingState<Int, T>,
        getRemoteKeyByEntity: suspend (T) -> R
    ): R? = state.pages.lastOrNull {
        it.data.isNotEmpty()
    }?.data?.lastOrNull()?.let { entity ->
        getRemoteKeyByEntity(entity)
    }
}

internal val defaultPagingConfig = PagingConfig(pageSize = PAGE_SIZE)
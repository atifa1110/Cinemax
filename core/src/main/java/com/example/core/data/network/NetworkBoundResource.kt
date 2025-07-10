package com.example.core.data.network

import com.example.core.data.network.response.CinemaxResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> CinemaxResponse<RequestType>,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<CinemaxResponse<ResultType>> = flow {
    emit(CinemaxResponse.Loading)
    val flow = if (shouldFetch(query().first())) { //get first and pass it.
        emit(CinemaxResponse.Loading)

        when (val response = fetch()) {
            is CinemaxResponse.Success -> {
                saveFetchResult(response.value)
                query().map { CinemaxResponse.success(it) }
            }
            is CinemaxResponse.Failure -> {
                query().map { CinemaxResponse.failure(response.code, response.error) }
            }
            else -> error("Unhandled state: $response")
        }
    } else {
        query().map { CinemaxResponse.success(it) }
    }

    emitAll(flow)
}

package com.example.core.data.network

import com.example.core.data.network.response.CinemaxResponse
import com.example.core.data.network.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response

abstract class BaseRemoteDataSource(
    private val gson: Gson
) {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): CinemaxResponse<T> {
        return try {
            val response = apiCall()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                CinemaxResponse.success(body)
            } else {
                val errorResponse = response.errorBody()?.string()
                val error = errorResponse?.let {
                    try {
                        Gson().fromJson(it, ErrorResponse::class.java)
                    } catch (e: Exception) {
                        ErrorResponse(-1, "Malformed error response",false)
                    }
                } ?: ErrorResponse(-1, "Unknown error",false)

                CinemaxResponse.failure(response.code(),error.statusMessage)
            }
        } catch (e: Exception) {
            CinemaxResponse.failure(-1, e.localizedMessage ?: "Network error")
        }
    }
}

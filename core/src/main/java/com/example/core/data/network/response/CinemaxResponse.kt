package com.example.core.data.network.response

sealed class CinemaxResponse<out T> {
    data class Success<T>(val value: T) : CinemaxResponse<T>()
    data class Failure(val code: Int, val error: String) : CinemaxResponse<Nothing>()
    object Loading : CinemaxResponse<Nothing>()

    companion object {
        fun <T> success(data: T): Success<T> = Success(data)
        fun failure(code: Int, error: String): Failure = Failure(code, error)
    }
}


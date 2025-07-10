package com.example.core.data.repository.utils

import com.example.core.data.local.database.MovieDatabase
import com.example.core.data.local.util.DatabaseTransactionProvider
import org.mockito.Mockito.mock

class FakeTransactionProvider : DatabaseTransactionProvider(
    mock(MovieDatabase::class.java)
) {
    override suspend fun <R> runWithTransaction(block: suspend () -> R): R {
        return block()
    }
}

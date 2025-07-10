package com.example.core.data.local.dao.tv

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.data.local.database.MovieDatabase
import com.example.core.data.local.model.tv.TvShowRemoteKeyEntity
import com.example.core.data.local.util.DatabaseMediaType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TvShowRemoteKeyDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var tvShowRemoteKeyDao: TvShowRemoteKeyDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        tvShowRemoteKeyDao = database.tvShowRemoteKeyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndRetrieve() = runTest {
        val remoteKey = TvShowRemoteKeyEntity(1,DatabaseMediaType.TvShow.Popular,1,2)
        tvShowRemoteKeyDao.insertAll(listOf(remoteKey))

        val result = tvShowRemoteKeyDao.getByIdAndMediaType(1, DatabaseMediaType.TvShow.Popular)
        assertEquals(remoteKey, result)
    }

    @Test
    fun testDeleteByMediaType() = runTest {
        val remoteKeys = listOf(
            TvShowRemoteKeyEntity(1, DatabaseMediaType.TvShow.Popular,1,2),
            TvShowRemoteKeyEntity(2, DatabaseMediaType.TvShow.Trending,1,2)
        )
        tvShowRemoteKeyDao.insertAll(remoteKeys)
        tvShowRemoteKeyDao.deleteByMediaType(DatabaseMediaType.TvShow.Popular)

        val result = tvShowRemoteKeyDao.getByIdAndMediaType(1, DatabaseMediaType.TvShow.Popular)
        Assert.assertNull(result)
    }

}
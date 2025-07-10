package com.example.core.data.local.dao.wishlist

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.data.local.database.MovieDatabase
import com.example.core.data.local.model.wishlist.WishlistEntity
import com.example.core.data.local.util.DatabaseMediaType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WishlistDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var wishlistDao: WishlistDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        wishlistDao = database.wishListDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    val wishlistEntity = WishlistEntity(
        id = 1,
        networkId = 999,
        mediaType = DatabaseMediaType.Wishlist.Movie,
        title = "Red One",
        genreEntities = null,
        rating = 0.0,
        posterPath = "",
        isWishListed = true
    )

    @Test
    fun insertWishlistReturnGetByMediaType() = runTest {
        wishlistDao.insert(wishlistEntity)

        val wishlist = wishlistDao.getByMediaType().first()
        assertEquals(1, wishlist.size)
        assertEquals("Red One", wishlist[0].title)
    }

    @Test
    fun deleteByMediaTypeReturnGetByMediaType() = runTest {
        wishlistDao.insert(wishlistEntity)
        wishlistDao.deleteByMediaTypeAndNetworkId(DatabaseMediaType.Wishlist.Movie, 999)

        val wishlist = wishlistDao.getByMediaType().first()
        assertTrue(wishlist.isEmpty())
    }

    @Test
    fun insertWishlistCheckIsWishListExist() = runTest {
        wishlistDao.insert(wishlistEntity)

        val exists = wishlistDao.isWishListed(DatabaseMediaType.Wishlist.Movie, 999)
        assertTrue(exists)
    }

    @Test
    fun insertWishlistCheckIsWishListNotExist() = runTest {
        wishlistDao.insert(wishlistEntity)
        wishlistDao.deleteByMediaTypeAndNetworkId(DatabaseMediaType.Wishlist.Movie, 999)
        val notExists = wishlistDao.isWishListed(DatabaseMediaType.Wishlist.Movie, 999)
        assertFalse(notExists)
    }

}
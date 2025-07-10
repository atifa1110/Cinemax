package com.example.core.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

class DataStorePreferenceTest {

    private lateinit var context: Context
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var dataStorePreference: DataStorePreference

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        val testFileName = "test_user_prefs_${System.currentTimeMillis()}"
        dataStore = PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(testFileName)
        }
        dataStorePreference = DataStorePreference(dataStore)
    }

    @After
    fun tearDown() {
        context.preferencesDataStoreFile("test_user_prefs").delete()
    }

    @Test
    fun saveOnBoardingStateTrueOnBoardingStatesCorrectly() = runTest {
        dataStorePreference.saveOnBoardingState(true)
        val result = dataStorePreference.onBoardingState().first()
        assertTrue(result)
    }

    @Test
    fun saveOnBoardingStateFalseOnBoardingStatesCorrectly() =  runTest {
        dataStorePreference.saveOnBoardingState(false)
        val result = dataStorePreference.onBoardingState().first()
        assertFalse(result)
    }

    @Test
    fun saveOnLoginStateTrueOnLoginStateCorrectly() =  runTest {
        dataStorePreference.saveOnLoginState(true)
        val result = dataStorePreference.onLoginState().first()
        assertTrue(result)
    }

    @Test
    fun saveOnLoginStateFalseOnLoginStateCorrectly() =  runTest {
        dataStorePreference.saveOnLoginState(false)
        val result = dataStorePreference.onLoginState().first()
        assertFalse(result)
    }

}
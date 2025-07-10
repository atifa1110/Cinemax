package com.example.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.preferencesOf
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DataStorePreferenceTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var dataStorePreference: DataStorePreference

    @Before
    fun setup() {
        dataStore = mockk()
        dataStorePreference = DataStorePreference(dataStore)
    }

    @Test
    fun `saveOnBoardingState true and onBoardingState should work correctly`() = runTest {
        // Arrange: buat fake preferences
        val prefs = preferencesOf(booleanPreferencesKey("onboarding_state") to true)
        every { dataStore.data } returns flowOf(prefs)
        val result = dataStorePreference.onBoardingState().first()
        assertTrue(result)
    }

    @Test
    fun `saveOnBoardingState false and onBoardingState should work correctly`() =  runTest {
        // Arrange: buat fake preferences
        val prefs = preferencesOf(booleanPreferencesKey("onboarding_state") to false)
        every { dataStore.data } returns flowOf(prefs)
        val result = dataStorePreference.onBoardingState().first()
        assertFalse(result)
    }

    @Test
    fun `saveOnLoginState true and onLoginState should work correctly`() =  runTest {
        // Arrange: buat fake preferences
        val prefs = preferencesOf(booleanPreferencesKey("login_state") to true)
        every { dataStore.data } returns flowOf(prefs)
        val result = dataStorePreference.onLoginState().first()
        assertTrue(result)
    }

    @Test
    fun `saveOnLoginState false and onLoginState should work correctly`() =  runTest {
        // Arrange: buat fake preferences
        val prefs = preferencesOf(booleanPreferencesKey("login_state") to false)
        every { dataStore.data } returns flowOf(prefs)
        val result = dataStorePreference.onLoginState().first()
        assertFalse(result)
    }

}
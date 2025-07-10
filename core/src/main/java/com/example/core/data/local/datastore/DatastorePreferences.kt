package com.example.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStorePreference @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val ONBOARDING_STATE_KEY = booleanPreferencesKey("onboarding_state")
        private val LOGIN_STATE_KEY = booleanPreferencesKey("login_state")
    }

    // save boarding state
    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_STATE_KEY] = completed
        }
    }

    // get boarding state
    fun onBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[ONBOARDING_STATE_KEY] ?: false
                onBoardingState
            }
    }

    // save login state
    suspend fun saveOnLoginState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATE_KEY] = completed
        }

    }

    // get login state
    fun onLoginState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onLoginState = preferences[LOGIN_STATE_KEY] ?: false
                onLoginState
            }
    }
}
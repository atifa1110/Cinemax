package com.example.core.data.repository.datastore

import com.example.core.data.local.datastore.DataStorePreference
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DataStoreRepositoryImplTest {

    private lateinit var dataStorePreference: DataStorePreference
    private lateinit var dataStoreRepository: DataStoreRepositoryImpl

    // Test dispatcher for coroutine testing
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        dataStorePreference = mockk()
        dataStoreRepository = DataStoreRepositoryImpl(dataStorePreference)
    }

    @Test
    fun `saveOnLoginState should call saveOnLoginState in dataStorePreference`() = runTest(testDispatcher) {
        val completed = true
        coEvery { dataStorePreference.saveOnLoginState(completed) } just Runs
        dataStoreRepository.saveOnLoginState(completed)
        coVerify { dataStorePreference.saveOnLoginState(completed) }
    }

    @Test
    fun `saveOnBoardingState should call saveOnBoardingState in dataStorePreference`() = runTest(testDispatcher) {
        // Arrange
        val completed = true
        coEvery { dataStorePreference.saveOnBoardingState(completed)} just Runs
        dataStoreRepository.saveOnBoardingState(completed)
        coVerify { dataStorePreference.saveOnBoardingState(completed)}
    }

    @Test
    fun `onLoginState should return flow from dataStorePreference`() = runTest(testDispatcher) {
        val expectedFlow = flowOf(true)
        coEvery { dataStorePreference.onLoginState()} returns expectedFlow

        val result = dataStoreRepository.onLoginState().first()
        assertEquals(true, result)
        coVerify { dataStorePreference.onLoginState()}
    }

    @Test
    fun `onBoardingState should return flow from dataStorePreference`() = runTest(testDispatcher) {
        // Arrange
        val expectedFlow = flowOf(false)
        coEvery { dataStorePreference.onBoardingState() } returns expectedFlow
        val result = dataStoreRepository.onBoardingState().first()
        assertEquals(false, result)
        coVerify {  dataStorePreference.onBoardingState() }
    }

}
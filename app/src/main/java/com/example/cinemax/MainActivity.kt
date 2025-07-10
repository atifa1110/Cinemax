package com.example.cinemax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.cinemax.navigation.AuthDestination
import com.example.cinemax.navigation.CinemaxNavHost
import com.example.cinemax.navigation.MainDestination
import com.example.cinemax.navigation.OnBoardingDestination
import com.example.cinemax.screen.splash.SplashViewModel
import com.example.cinemax.ui.theme.CinemaxTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        // Keep splash screen until loading finishes
        splashScreen.setKeepOnScreenCondition {
            !splashViewModel.isLoadingComplete.value
        }

        super.onCreate(savedInstanceState)

        setContent {
            val isBoarding by splashViewModel.onBoardingState.collectAsStateWithLifecycle(false)
            val isLogin by splashViewModel.onLoginState.collectAsStateWithLifecycle(false)
            val isLoadingComplete by splashViewModel.isLoadingComplete.collectAsStateWithLifecycle(false)

            val startDestination = when {
                // your conditions here
                !isBoarding -> OnBoardingDestination
                !isLogin -> AuthDestination
                else -> MainDestination
            }

            if (isLoadingComplete) {
                CinemaxTheme {
                    val navController = rememberNavController()
                    CinemaxNavHost(
                        navController = navController,
                        startDestination = startDestination,
                        onBackButtonClick = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}

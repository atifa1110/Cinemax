package com.example.cinemax.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.cinemax.screen.available.NotAvailableRoute

object DownloadDestination : CinemaxNavigationDestination {
    override val route = "download_route"
    override val destination = "download_destination"
}

fun NavGraphBuilder.downloadGraph(
) = composable(route = DownloadDestination.route) {
    NotAvailableRoute()
}

package com.example.cinemax.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.cinemax.R

enum class TopLevelDestination(
    override val route: String,
    override val destination: String,
    @DrawableRes val iconResourceId: Int,
    @StringRes val textResourceId: Int
) : CinemaxNavigationDestination {

    Home(
        route = HomeDestination.route,
        destination = HomeDestination.destination,
        iconResourceId = R.drawable.ic_home,
        textResourceId = R.string.home
    ),
    Search(
        route = SearchDestination.route,
        destination = SearchDestination.destination,
        iconResourceId = R.drawable.ic_search,
        textResourceId = R.string.search
    ),
    Download(
        route = DownloadDestination.route,
        destination = DownloadDestination.destination,
        iconResourceId = R.drawable.ic_download,
        textResourceId = R.string.download
    ),
    Profile(
        route = ProfileDestination.route,
        destination = ProfileDestination.destination,
        iconResourceId = R.drawable.ic_profile,
        textResourceId = R.string.profile
    )
}
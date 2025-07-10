package com.example.cinemax.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.Grey
import com.example.cinemax.ui.theme.Soft

@Composable
fun NavigationBottomBar(
    items: Array<TopLevelDestination>,
    currentDestination: NavDestination?,
    navController: NavHostController,
){
    NavigationBar(
        containerColor = Dark,
    ) {
        items.forEach{ item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.route == item.route
                } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(text = item.name)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BlueAccent,
                    unselectedIconColor = Grey,
                    indicatorColor = Soft,
                    selectedTextColor = BlueAccent,
                    unselectedTextColor = Grey
                ),
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResourceId),
                        contentDescription = stringResource(id = item.textResourceId)
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview(){
    val topLevelDestinations = TopLevelDestination.entries.toTypedArray()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    CinemaxTheme {
            NavigationBottomBar(
                items = topLevelDestinations,
                currentDestination = currentDestination,
                navController = navController
            )
    }
}
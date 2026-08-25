package com.studyProject.rickandmorty.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

private val BottomBarHeight = 80.dp

private data class BottomBarItem(
    val screen: Screen,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
)

private val bottomBarItems = listOf(
    BottomBarItem(Screen.Discover, "Discover", Icons.Filled.Home),
    BottomBarItem(Screen.Favorites, "Favorites", Icons.Filled.Favorite),
)

@Composable
fun RickAndMortyBottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {
    val titleColor = MaterialTheme.colorScheme.onBackground

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.height(BottomBarHeight),
    ) {
        bottomBarItems.forEach { item ->
            val selected = when (item.screen) {
                Screen.Discover -> currentDestination?.hasRoute<Screen.Discover>() == true
                Screen.Favorites -> currentDestination?.hasRoute<Screen.Favorites>() == true
                else -> false
            }

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.screen) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label, tint = titleColor) },
                label = { Text(item.label, color = titleColor) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = titleColor,
                    unselectedIconColor = titleColor,
                    selectedTextColor = titleColor,
                    unselectedTextColor = titleColor,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    }
}

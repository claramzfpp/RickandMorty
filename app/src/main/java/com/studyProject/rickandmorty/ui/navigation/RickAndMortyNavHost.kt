package com.studyProject.rickandmorty.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.studyProject.rickandmorty.ui.allcharacters.AllCharactersScreen
import com.studyProject.rickandmorty.ui.alllocations.AllLocationsScreen
import com.studyProject.rickandmorty.ui.characterdetail.CharacterDetailScreen
import com.studyProject.rickandmorty.ui.discover.DiscoverScreen
import com.studyProject.rickandmorty.ui.favorites.FavoritesScreen
import com.studyProject.rickandmorty.ui.locationdetail.LocationDetailScreen
import com.studyProject.rickandmorty.ui.seasonepisodes.SeasonEpisodesScreen


// NavController == NavigationPath (swift)
// NavHost == NavigationStack (swift)

@Composable
fun RickAndMortyNavHost(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val showBottomBar = currentDestination?.hasRoute<Screen.Discover>() == true ||
        currentDestination?.hasRoute<Screen.Favorites>() == true

    Scaffold(
        // sem topBar aqui; cada tela já reserva o inset da status bar na própria Scaffold.
        // Sem isso, o inset do sistema (status bar / gesture bar) seria contado duas vezes.
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                RickAndMortyBottomBar(navController = navController, currentDestination = currentDestination)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Discover,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<Screen.Discover> {
                DiscoverScreen(
                    onCharacterClick = { characterId ->
                        navController.navigate(Screen.CharacterDetail(characterId))
                    },
                    onSeeAllClick = { navController.navigate(Screen.AllCharacters) },
                    onSeeAllLocationsClick = { navController.navigate(Screen.AllLocations) },
                    onLocationClick = { locationId ->
                        navController.navigate(Screen.LocationDetail(locationId))
                    },
                    onSeasonClick = { seasonNumber ->
                        navController.navigate(Screen.SeasonEpisodes(seasonNumber))
                    },
                )
            }
            composable<Screen.Favorites> {
                FavoritesScreen(
                    onCharacterClick = { characterId ->
                        navController.navigate(Screen.CharacterDetail(characterId))
                    },
                )
            }
            composable<Screen.AllCharacters> {
                AllCharactersScreen(
                    onBackClick = { navController.popBackStack() },
                    onCharacterClick = { characterId ->
                        navController.navigate(Screen.CharacterDetail(characterId))
                    },
                )
            }
            composable<Screen.AllLocations> {
                AllLocationsScreen(
                    onBackClick = { navController.popBackStack() },
                    onLocationClick = { locationId ->
                        navController.navigate(Screen.LocationDetail(locationId))
                    },
                )
            }
            composable<Screen.CharacterDetail> {
                CharacterDetailScreen(
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable<Screen.LocationDetail> {
                LocationDetailScreen(
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable<Screen.SeasonEpisodes> {
                SeasonEpisodesScreen(
                    onBackClick = { navController.popBackStack() },
                )
            }
        }
    }
}
/*
o código de cima em swiftui ficaria assim:

NavigationStack(path: $path) {
    DiscoverScreen()
        .navigationDestination(for: CharacterDetail.self) { detail in
            CharacterDetailScreen(id: detail.characterId)
        }
}

 */

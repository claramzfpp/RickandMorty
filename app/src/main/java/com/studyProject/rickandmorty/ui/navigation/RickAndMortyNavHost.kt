package com.studyProject.rickandmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.studyProject.rickandmorty.ui.characterdetail.CharacterDetailScreen
import com.studyProject.rickandmorty.ui.discover.DiscoverScreen


// NavController == NavigationPath (swift)
// NavHost == NavigationStack (swift)

@Composable
fun RickAndMortyNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Discover) {
        composable<Screen.Discover> {
            DiscoverScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(Screen.CharacterDetail(characterId))
                },
            )
        }
        composable<Screen.CharacterDetail> {
            CharacterDetailScreen(
                onBackClick = { navController.popBackStack() },
            )
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

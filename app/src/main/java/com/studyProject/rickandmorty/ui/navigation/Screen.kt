package com.studyProject.rickandmorty.ui.navigation

import kotlinx.serialization.Serializable

@Serializable //mesma coisa que um Codable/Hashable
sealed interface Screen {

    @Serializable
    data object Discover : Screen

    @Serializable
    data object Favorites : Screen

    @Serializable
    data object AllCharacters : Screen

    @Serializable
    data object AllLocations : Screen

    @Serializable
    data class CharacterDetail(val characterId: Int) : Screen
}

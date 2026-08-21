package com.studyProject.rickandmorty.ui.navigation

import kotlinx.serialization.Serializable

@Serializable //mesma coisa que um Codable/Hashable
sealed interface Screen {

    @Serializable
    data object Discover : Screen

    @Serializable
    data class CharacterDetail(val characterId: Int) : Screen
}

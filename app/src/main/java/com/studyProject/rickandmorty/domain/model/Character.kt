package com.studyProject.rickandmorty.domain.model

// Modelo de DOMÍNIO: limpo, sem @Serializable, sem nada da API.
// É o que a UI e o resto do app usam — não o DTO.
// Só carrega os campos que o app realmente precisa.
data class Character(
    val id: Int,
    val name: String,
    val status: String,     // texto de exibição: "Alive" / "Dead" / "Unknown"
    val species: String,
    val gender: String,
    val imageUrl: String,
    val originName: String
)

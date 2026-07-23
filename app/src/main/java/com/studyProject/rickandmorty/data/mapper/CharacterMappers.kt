package com.studyProject.rickandmorty.data.mapper

import com.studyProject.rickandmorty.data.remote.dto.RMCharacter
import com.studyProject.rickandmorty.domain.model.Character

// Converte o DTO (formato da API) no modelo de domínio (formato do app).
// Fica na camada `data` porque é ela que conhece o DTO.
// É uma "extension function": adiciona um método .toDomain() no RMCharacter.
fun RMCharacter.toDomain(): Character = Character(
    id = id,
    name = name,
    status = status.text,        // aqui traduzimos o enum pro texto de exibição
    species = species,
    gender = gender,
    imageUrl = image,
    originName = origin.name
)

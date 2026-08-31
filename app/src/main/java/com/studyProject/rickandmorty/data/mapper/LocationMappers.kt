package com.studyProject.rickandmorty.data.mapper

import com.studyProject.rickandmorty.data.remote.dto.RMLocation
import com.studyProject.rickandmorty.domain.model.Location

fun RMLocation.toDomain(): Location = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)

package com.studyProject.rickandmorty.data.mapper

import com.studyProject.rickandmorty.data.remote.dto.RMEpisode
import com.studyProject.rickandmorty.domain.model.Episode

private val EPISODE_CODE_REGEX = Regex("[Ss](\\d+)[Ee](\\d+)")

fun RMEpisode.toDomain(): Episode {
    val (season, episodeNumber) = parseSeasonAndEpisode(episode)

    return Episode(
        id = id,
        name = name,
        airDate = airDate,
        episodeCode = episode,
        season = season,
        episodeNumber = episodeNumber
    )
}

private fun parseSeasonAndEpisode(episodeCode: String): Pair<Int, Int> {
    val match = EPISODE_CODE_REGEX.find(episodeCode)
        ?: return 0 to 0

    val (season, episodeNumber) = match.destructured
    return season.toInt() to episodeNumber.toInt()
}

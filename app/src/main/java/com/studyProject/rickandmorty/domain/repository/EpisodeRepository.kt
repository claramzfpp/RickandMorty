package com.studyProject.rickandmorty.domain.repository

import com.studyProject.rickandmorty.domain.model.Episode

interface EpisodeRepository {

    suspend fun getEpisodesBySeason(season: Int): List<Episode>
}

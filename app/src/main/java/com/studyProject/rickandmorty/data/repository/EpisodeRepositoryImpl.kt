package com.studyProject.rickandmorty.data.repository

import com.studyProject.rickandmorty.data.mapper.toDomain
import com.studyProject.rickandmorty.data.remote.RickAndMortyApi
import com.studyProject.rickandmorty.domain.model.Episode
import com.studyProject.rickandmorty.domain.repository.EpisodeRepository
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
) : EpisodeRepository {

    override suspend fun getEpisodesBySeason(season: Int): List<Episode> {
        return fetchAllEpisodes().filter { it.season == season }
    }

    override suspend fun getEpisodeCountsBySeason(): Map<Int, Int> {
        return fetchAllEpisodes().groupingBy { it.season }.eachCount()
    }

    private suspend fun fetchAllEpisodes(): List<Episode> {
        val episodes = mutableListOf<Episode>()

        var page: Int? = 1
        while (page != null) {
            val response = api.fetchingEpisodes(page = page)
            episodes += response.results.map { it.toDomain() }

            page = if (response.info.next != null) page + 1 else null
        }

        return episodes
    }
}

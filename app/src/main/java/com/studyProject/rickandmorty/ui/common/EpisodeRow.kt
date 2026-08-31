package com.studyProject.rickandmorty.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyProject.rickandmorty.domain.model.Episode
import com.studyProject.rickandmorty.ui.theme.RMBrown
import com.studyProject.rickandmorty.ui.theme.RMGreen
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun EpisodeRow(
    episode: Episode,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp),
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Tv,
                    contentDescription = null,
                    tint = RMGreen,
                    modifier = Modifier.size(20.dp),
                )
            }

            Column(
                modifier = Modifier.padding(start = 15.dp),
            ) {
                Text(
                    text = episode.name,
                    color = RMBrown,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                )

                Text(
                    text = episode.airDate,
                    color = Color.DarkGray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 5.dp),
                )
            }
        }
    }
}

private val previewEpisode = Episode(
    id = 1,
    name = "Pilot",
    airDate = "December 2, 2013",
    episodeCode = "S01E01",
    season = 1,
    episodeNumber = 1,
)

@Preview(showBackground = true)
@Composable
private fun EpisodeRowPreview() {
    RickAndMortyTheme {
        EpisodeRow(episode = previewEpisode)
    }
}

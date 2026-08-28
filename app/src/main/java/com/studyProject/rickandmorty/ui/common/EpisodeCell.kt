package com.studyProject.rickandmorty.ui.common

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.studyProject.rickandmorty.ui.theme.RMGreen
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.Dp
import com.studyProject.rickandmorty.ui.theme.RMBrown
import com.studyProject.rickandmorty.ui.theme.RMPink
import com.studyProject.rickandmorty.ui.theme.RMYellow

@Composable
fun EpisodeCell(
    seasonNumber: Int,
    episodesNumber: Int
) {
    Box(
        modifier = Modifier
            .size(130.dp, 129.dp)
            .background(
                color = Color.LightGray.copy(alpha = 0.8f), //precisa fazer um copy para alterar o alpha
                shape = RoundedCornerShape(10.dp)
            )
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp),
        ) {
            EpisodeInfo(seasonNumber, episodesNumber)
        }
    }
}

@Composable
private fun EpisodeInfo(
    seasonNumber: Int,
    episodesNumber: Int
) {
    Column() {
        val colorArray: Array<Color> = arrayOf(RMPink, RMGreen, RMYellow)

        Box(
            modifier = Modifier
                .size(width = 42.dp, height = 7.dp)
                .background(
                    color = colorArray[(seasonNumber - 1) % colorArray.size],
                    shape = RoundedCornerShape(12.dp)
                )
        )

        Text(
            text = seasonNumber.toString(),
            color = RMBrown,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 10.dp)
        )

        Text(
            text = "SEASON",
            color = RMBrown,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "$episodesNumber episodes",
            color = Color.DarkGray,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun CharacterCellGridPreview1() {
    RickAndMortyTheme {
        EpisodeCell(
            1,
            11
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun CharacterCellGridPreview2() {
    RickAndMortyTheme {
        EpisodeCell(
            2,
            8
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun CharacterCellGridPreview3() {
    RickAndMortyTheme {
        EpisodeCell(
            3,
            12
        )
    }
}
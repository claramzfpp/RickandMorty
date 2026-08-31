package com.studyProject.rickandmorty.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyProject.rickandmorty.ui.theme.RMBrown
import com.studyProject.rickandmorty.ui.theme.RMGreen
import com.studyProject.rickandmorty.ui.theme.RMPink
import com.studyProject.rickandmorty.ui.theme.RMYellow
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun LocationCell(
    id: Int,
    name: String,
    type: String,
    dimension: String
) {
    Box(
        modifier = Modifier
            .size(160.dp, 98.dp)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp),
        ) {
            LocationInfo(id, name, type, dimension)
        }
    }
}

@Composable
private fun LocationInfo(
    id: Int,
    name: String,
    type: String,
    dimension: String
) {
    Column() {
        val colorArray: Array<Color> = arrayOf(RMPink, RMGreen, RMYellow)

        Icon(
            imageVector = locationTypeIcon(type),
            contentDescription = type,
            tint = colorArray[(id - 1) % colorArray.size],
            modifier = Modifier.size(22.dp)
        )

        Text(
            text = name,
            color = RMBrown,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier
                .padding(top = 5.dp)
        )

        Text(
            text = dimension.replaceFirstChar { it.uppercase() },
            color = Color.DarkGray,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

private fun locationTypeIcon(type: String): ImageVector {
    return when (type) {
        "Planet" -> Icons.Filled.Public
        "Cluster" -> Icons.Filled.Star
        "Space station" -> Icons.Default.RocketLaunch
        "TV" -> Icons.Default.Tv
        //adicionei alguns por diversão, não achei a lista com todas as opcões
        // teria que verificar todos os retornos da API
        else -> Icons.Filled.Place
    }
}

@Preview(showBackground = false)
@Composable
private fun LocationCellPreview() {
    RickAndMortyTheme {
        LocationCell(
            1,
            "Earth",
            "Planet",
            "Dimension C-137"
        )
    }
}

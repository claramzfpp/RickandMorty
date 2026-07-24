package com.studyProject.rickandmorty.ui.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun DiscoverCharacterCell(name: String, status: String, image: String) {
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        imageComponent(image)

        labelComponent(name, status)
    }
}

@Composable
fun labelComponent(name: String, status: String) {
    val roundedCornerShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomEnd = 8.dp,
        bottomStart = 8.dp
    )

    return Column(
        modifier = Modifier
            .width(170.dp)
            .clip(roundedCornerShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 7.dp, vertical = 5.dp)
    ) {
        Text(
            text = "$name",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Status: $status",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun imageComponent(image: String) {
    Box(
        modifier = Modifier
            .size(170.dp, 180.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop, // = .scaledToFill() do SwiftUI
            modifier = Modifier
                .size(170.dp, 180.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background) // cor de fundo enquanto a imagem carrega
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DiscoverCharacterCellPreview() {
    RickAndMortyTheme {
        DiscoverCharacterCell(
            "Rick Sanchez",
            "Alive",
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        )
    }
}
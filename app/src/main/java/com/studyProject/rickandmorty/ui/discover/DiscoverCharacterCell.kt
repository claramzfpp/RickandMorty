package com.studyProject.rickandmorty.ui.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.studyProject.rickandmorty.domain.model.Character
import com.studyProject.rickandmorty.ui.theme.RMGray
import com.studyProject.rickandmorty.ui.theme.RMGreen
import com.studyProject.rickandmorty.ui.theme.RMRed
import com.studyProject.rickandmorty.ui.theme.RMYellow
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun DiscoverCharacterCell(
    character: Character,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.clickable(onClick = onClick),
        contentAlignment = Alignment.BottomCenter,
    ) {
        CharacterImage(character.imageUrl)
        CharacterLabel(character.name, character.status)
    }
}

@Composable
private fun CharacterLabel(name: String, status: String) {
    val roundedCornerShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomEnd = 8.dp,
        bottomStart = 8.dp,
    )

    Column(
        modifier = Modifier
            .width(140.dp)
            .height(72.dp)
            .clip(roundedCornerShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp, 10.dp, 8.dp)
    ) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )

        StatusBadge(status)
    }
}

@Composable
private fun StatusBadge(status: String) {
    val statusColor = when (status.lowercase()) {
        "alive" -> RMGreen
        "dead" -> RMRed
        else -> RMYellow
    }

    Box(
        modifier = Modifier
            .height(24.dp)
            .background(RMGray, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(statusColor, CircleShape),
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = status,
                color = statusColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun CharacterImage(imageUrl: String) {
    Box(
        modifier = Modifier
            .size(140.dp, 212.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop, // = .scaledToFill() do SwiftUI
            loading = {
                // spinner enquanto a imagem baixa
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            },
            modifier = Modifier
                .size(140.dp, 140.dp)
                .background(MaterialTheme.colorScheme.background), // cor de fundo enquanto a imagem carrega
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscoverCharacterCellPreview() {
    RickAndMortyTheme {
        DiscoverCharacterCell(
            Character(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                gender = "Male",
                imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                originName = "Earth (C-137)",
            )
        )
    }
}

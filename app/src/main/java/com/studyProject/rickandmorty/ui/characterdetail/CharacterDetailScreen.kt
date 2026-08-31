package com.studyProject.rickandmorty.ui.characterdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.studyProject.rickandmorty.domain.model.Character
import com.studyProject.rickandmorty.ui.common.ErrorContent
import com.studyProject.rickandmorty.ui.common.LoadingContent
import com.studyProject.rickandmorty.ui.common.SeasonCell
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    val background = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = background,
                    scrolledContainerColor = background,
                ),
            )
        },
        containerColor = background,
    ) { innerPadding ->
        val contentModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(background)

        when (val currentState = state) {
            CharacterDetailUiState.Loading -> LoadingContent(contentModifier)
            is CharacterDetailUiState.Loaded -> CharacterDetailContent(
                character = currentState.character,
                isFavorite = isFavorite,
                onToggleFavorite = viewModel::toggleFavorite,
                modifier = contentModifier,
            )
            is CharacterDetailUiState.Error -> ErrorContent(currentState.message, contentModifier)
        }
    }
}

@Composable
private fun CharacterDetailContent(
    character: Character,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        CharacterImage(
            imageUrl = character.imageUrl,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        CharacterName(
            name = character.name,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        CharacterInfoCard(character = character)

        FavoriteButton(
            isFavorite = isFavorite,
            onClick = onToggleFavorite,
        )
    }
}

@Composable
private fun CharacterImage(imageUrl: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(320.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(320.dp)
        )
    }
}

@Composable
private fun CharacterName(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .padding(top = 16.dp),
    )
}

@Composable
private fun CharacterInfoCard(character: Character, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 16.dp)
        ) {
            CharacterDetailRow(label = "Status", value = character.status)
            CharacterDetailRow(label = "Species", value = character.species)
            CharacterDetailRow(label = "Gender", value = character.gender)
            CharacterDetailRow(label = "Origin", value = character.originName)
        }
    }
}

@Composable
private fun CharacterDetailRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
        ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 20.sp,
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun FavoriteButton(isFavorite: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val contentColor = if (isFavorite) Color.White else MaterialTheme.colorScheme.primary
    val containerColor = if (isFavorite) MaterialTheme.colorScheme.primary else Color.Transparent

    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite Icon",
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )

            Spacer(Modifier.width(ButtonDefaults.IconSpacing))

            Text(
                text = "Favorite",
                color = contentColor,
                fontSize = 20.sp,
            )
        }
    }
}

private val previewCharacter = Character(
    id = 1,
    name = "Rick Sanchez",
    status = "Alive",
    species = "Human",
    gender = "Male",
    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
    originName = "Earth (C-137)",
)

@Preview(showBackground = true)
@Composable
private fun CharacterDetailContentPreview() {
    RickAndMortyTheme {
        CharacterDetailContent(
            character = previewCharacter,
            isFavorite = false,
            onToggleFavorite = {},
        )
    }
}

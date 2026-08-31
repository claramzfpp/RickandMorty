package com.studyProject.rickandmorty.ui.characterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPinCircle
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.studyProject.rickandmorty.domain.model.Character
import com.studyProject.rickandmorty.ui.common.CharacterInfoCard
import com.studyProject.rickandmorty.ui.common.ErrorContent
import com.studyProject.rickandmorty.ui.common.LoadingContent
import com.studyProject.rickandmorty.ui.common.StatusBadge
import com.studyProject.rickandmorty.ui.theme.RMBrown
import com.studyProject.rickandmorty.ui.theme.RMGreen
import com.studyProject.rickandmorty.ui.theme.RMPink
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
    val background = RMBrown

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .background(Color.Black.copy(alpha = 0.35f), CircleShape),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = viewModel::toggleFavorite,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(Color.Black.copy(alpha = 0.35f), CircleShape),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Toggle favorite",
                            tint = if (isFavorite) RMPink else Color.White,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                ),
            )
        },
        containerColor = background,
    ) { innerPadding ->
        val contentModifier = Modifier
            .fillMaxSize()
            .padding(bottom = innerPadding.calculateBottomPadding())
            .background(background)

        when (val currentState = state) {
            CharacterDetailUiState.Loading -> LoadingContent(contentModifier)
            is CharacterDetailUiState.Loaded -> CharacterDetailContent(
                character = currentState.character,
                modifier = contentModifier,
            )
            is CharacterDetailUiState.Error -> ErrorContent(currentState.message, contentModifier)
        }
    }
}

@Composable
private fun CharacterDetailContent(
    character: Character,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        CharacterImage(
            imageUrl = character.imageUrl,
            modifier = Modifier.fillMaxWidth(),
        )

        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CharacterName(name = character.name)

                StatusBadge(
                    status = character.status,
                    width = 92,
                    height = 36,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
            ) {
                CharacterInfoCard(
                    icon = Icons.Filled.Science,
                    title = "Species",
                    subTitle = character.species,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 15.dp)
                )

                CharacterInfoCard(
                    icon = Icons.Filled.Person,
                    title = "Gender",
                    subTitle = character.gender,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 15.dp),
                )

                CharacterInfoCard(
                    icon = Icons.Filled.PersonPinCircle,
                    title = "Origin",
                    subTitle = character.originName,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp),
                )
            }
        }
    }
}

@Composable
private fun CharacterImage(imageUrl: String, modifier: Modifier = Modifier) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .size(width = 390.dp, height = 460.dp)
            .background(MaterialTheme.colorScheme.background)
    )
}

@Composable
private fun CharacterName(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        color = RMGreen,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .padding(top = 16.dp),
    )
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
        )
    }
}

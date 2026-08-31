package com.studyProject.rickandmorty.ui.locationdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.studyProject.rickandmorty.domain.model.Location
import com.studyProject.rickandmorty.ui.common.ErrorContent
import com.studyProject.rickandmorty.ui.common.InfoCard
import com.studyProject.rickandmorty.ui.common.LoadingContent
import com.studyProject.rickandmorty.ui.common.locationTypeIcon
import com.studyProject.rickandmorty.ui.theme.RMBrown
import com.studyProject.rickandmorty.ui.theme.RMGreen
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LocationDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
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
            LocationDetailUiState.Loading -> LoadingContent(contentModifier)
            is LocationDetailUiState.Loaded -> LocationDetailContent(
                location = currentState.location,
                modifier = contentModifier,
            )
            is LocationDetailUiState.Error -> ErrorContent(currentState.message, contentModifier)
        }
    }
}

@Composable
private fun LocationDetailContent(
    location: Location,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        LocationHeroIcon(
            type = location.type,
            modifier = Modifier.fillMaxWidth(),
        )

        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            LocationName(name = location.name)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
            ) {
                InfoCard(
                    icon = locationTypeIcon(location.type),
                    title = "Type",
                    subTitle = location.type,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 15.dp)
                )

                InfoCard(
                    icon = Icons.Filled.Layers,
                    title = "Dimension",
                    subTitle = location.dimension.replaceFirstChar { it.uppercase() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp),
                )
            }
        }
    }
}

@Composable
private fun LocationHeroIcon(type: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .size(width = 390.dp, height = 260.dp)
            .background(Color.LightGray)
    ) {
        Icon(
            imageVector = locationTypeIcon(type),
            contentDescription = null,
            tint = RMBrown,
            modifier = Modifier.size(120.dp),
        )
    }
}

@Composable
private fun LocationName(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        color = RMGreen,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .padding(top = 16.dp),
    )
}

private val previewLocation = Location(
    id = 1,
    name = "Earth",
    type = "Planet",
    dimension = "Dimension C-137",
)

@Preview(showBackground = true)
@Composable
private fun LocationDetailContentPreview() {
    RickAndMortyTheme {
        LocationDetailContent(
            location = previewLocation,
        )
    }
}

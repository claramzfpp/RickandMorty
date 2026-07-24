package com.studyProject.rickandmorty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyProject.rickandmorty.ui.theme.RMBrown
import com.studyProject.rickandmorty.ui.theme.RMGray
import com.studyProject.rickandmorty.ui.theme.RMGreen
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverTabView() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        // 3. liga o scroll do conteúdo à barra
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { titleBar() },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = RMGray,
                    scrolledContainerColor = RMGray,
                    titleContentColor = RMGray
                    ),
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = RMGray
    ) { innerPadding ->
        // aplica o espaço da top bar pra o conteúdo não ficar atrás dela
        VerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(RMGray)
        )
    }
}

@Composable
fun VerticalGrid(modifier: Modifier) {
    LazyVerticalGrid(
        // 1. Define column strategy
        columns = GridCells.Fixed(2),

        // 2. Add structural padding around the whole grid
        contentPadding = PaddingValues(16.dp),

        // 3. Set spacing between cells
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),

        modifier = modifier
    ) {
        // 4. Populate with items
        items(20) { item ->
            DiscoverCharacterCell(
                "Rick Sanchez",
                "Alive",
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
            )
        }
    }
}

@Composable
fun titleBar() {
    Text(
        text = "Discover",
        fontSize = 50.sp,
        fontWeight = FontWeight.Bold,
        color = RMBrown
    )
}

@Preview(showBackground = true)
@Composable
fun DiscoverTabViewPreview() {
    RickAndMortyTheme {
        DiscoverTabView()
    }
}

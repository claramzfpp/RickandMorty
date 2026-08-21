package com.studyProject.rickandmorty.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Mapeia as cores RM pros papéis semânticos do Material.
// Muda AQUI e o app inteiro acompanha (todas as telas).
private val RmColorScheme = lightColorScheme(
    primary = RMGreen,               // acento / bordas
    onPrimary = RMBrown,
    secondary = RMYellow,
    tertiary = RMRed,
    background = RMGray,             // fundo da tela
    onBackground = RMBrown,          // texto sobre o fundo (ex.: título)
    surface = RMGray,               // superfícies / barras
    onSurface = RMBrown,
    surfaceVariant = RMBrown,       // cards (ex.: o label do personagem)
    onSurfaceVariant = Color.White, // texto sobre os cards
)

@Composable
fun RickAndMortyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = RmColorScheme,
        typography = Typography,
        content = content
    )
}

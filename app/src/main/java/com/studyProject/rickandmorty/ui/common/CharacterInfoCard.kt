package com.studyProject.rickandmorty.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyProject.rickandmorty.ui.theme.RMBrown
import com.studyProject.rickandmorty.ui.theme.RMGray
import com.studyProject.rickandmorty.ui.theme.RMGreen
import com.studyProject.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun CharacterInfoCard(
    icon: ImageVector,
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(92.dp)
            .background(RMGray, RoundedCornerShape(15.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = RMGreen,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .size(24.dp)
            )

            Text(
                text = title,
                color = Color.DarkGray,
                fontSize = 11.sp,
                lineHeight = 11.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(bottom = 5.dp)
            )

            Text(
                text = subTitle,
                color = RMBrown,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterInfoCardPreview() {
    RickAndMortyTheme {
        CharacterInfoCard(
            icon = Icons.Default.Favorite,
            title = "Species",
            subTitle = "Human",
        )
    }
}

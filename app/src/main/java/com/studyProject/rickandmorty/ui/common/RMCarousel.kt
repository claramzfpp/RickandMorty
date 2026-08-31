package com.studyProject.rickandmorty.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyProject.rickandmorty.ui.theme.RMBrown


@Composable
fun RMCarousel(
    title: String,
    // isso daqui não deveria estar para todos, o cliente que não precisa disso/tem mais acesso que o necessário
    // separar em dois components um com see all click e outro sem, seria overeng ou seguir boas práticas?
    // TODO: pensar em como fazer esse component melhor
    onSeeAllClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    shouldShowSeeAll: Boolean = true,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                color = RMBrown,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            if (shouldShowSeeAll) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = onSeeAllClick),
                ) {
                    Text(
                        text = "See all",
                        color = RMBrown,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = RMBrown,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }

        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun RMCarouselPreview() {
    RMCarousel(
        title = "Characters",
        onSeeAllClick = {},
    ) {
        Text("Sample content")
    }
}

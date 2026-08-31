package com.studyProject.rickandmorty.ui.common

import android.content.res.Resources
import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyProject.rickandmorty.ui.theme.RMGray
import com.studyProject.rickandmorty.ui.theme.RMGreen
import com.studyProject.rickandmorty.ui.theme.RMRed
import com.studyProject.rickandmorty.ui.theme.RMYellow

private const val BaseHeight = 24

@Composable
fun StatusBadge(
    status: String,
    backgroundColor: androidx.compose.ui.graphics.Color = RMGray,
    width: Int = 62,
    height: Int = BaseHeight
) {
    val statusColor = when (status.lowercase()) {
        "alive" -> RMGreen
        "dead" -> RMRed
        else -> RMYellow
    }

    val scale = height / BaseHeight.toFloat()

    Box(
        modifier = Modifier
            .size(width.dp, height.dp)
            .background(backgroundColor, RoundedCornerShape((height / 2).dp)),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp * scale),
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp * scale)
                    .background(statusColor, CircleShape),
            )

            Spacer(modifier = Modifier.width(5.dp * scale))

            Text(
                text = status,
                color = statusColor,
                fontSize = 10.sp * scale,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
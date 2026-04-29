package com.example.cpbuddy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GlassBox(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color.White.copy(alpha = 0.1f))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.3f),
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(16.dp)
    ) {
        Column {
            content()
        }
    }
}

@Composable
fun ImmersiveBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0F0F))
    ) {
        // Subtle blurred shapes for that ArchiveTune feel
        Box(
            modifier = Modifier
                .offset(x = (-50).dp, y = (-50).dp)
                .size(300.dp)
                .blur(100.dp)
                .background(Color(0x33BB86FC), RoundedCornerShape(150.dp))
        )
        Box(
            modifier = Modifier
                .align(androidx.compose.ui.Alignment.BottomEnd)
                .offset(x = 50.dp, y = 50.dp)
                .size(400.dp)
                .blur(120.dp)
                .background(Color(0x22FF4081), RoundedCornerShape(200.dp))
        )
        
        content()
    }
}

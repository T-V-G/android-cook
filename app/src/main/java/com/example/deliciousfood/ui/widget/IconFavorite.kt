package com.example.deliciousfood.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember

@Composable
fun IconFavorite(enable: Boolean , modifier: Modifier , onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Icon(
        imageVector = if (enable) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
        tint = if (enable) Color(0xffBE2724) else Color.DarkGray,
        contentDescription = null,
        modifier = modifier
            .size(24.dp)
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            )
    )
}



package com.example.deliciousfood.ui.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import com.example.deliciousfood.R

@Composable
fun RecipeIngredientTitle() {
    Text(
        text = stringResource(id = R.string.ingredients),
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        color = Color.White
    )
}

@Composable
fun RecipeIngredientItem(ingredientTitle: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
        text = "✓  $ingredientTitle",
        style = MaterialTheme.typography.titleSmall,
        color = Color.White
    )
}

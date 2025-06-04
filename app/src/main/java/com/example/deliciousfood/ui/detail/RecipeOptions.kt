

package com.example.deliciousfood.ui.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Stars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliciousfood.R
import com.example.deliciousfood.domain.model.RecipesItem
import com.example.deliciousfood.ui.common.IconFavorite

@Composable
fun RecipeOptions(
    recipe: RecipesItem ,
    clickFavorite: (RecipesItem) -> Unit
) {
    Row(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        TextButton(
            modifier = Modifier.weight(1f)
                .align(Alignment.CenterVertically),
            onClick = {}
        ) {
            Icon(
                modifier = Modifier.padding(end = 4.dp),
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "  ${recipe.aggregateLikes}",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp
            )
        }
        TextButton(
            modifier = Modifier.weight(1f)
                .align(Alignment.CenterVertically),
            onClick = { }
        ) {
            Icon(
                modifier = Modifier.padding(end = 4.dp),
                imageVector = Icons.Filled.Stars,
                contentDescription = null,
                tint = Color.White
            )
            Text(
                text = "  ${recipe.spoonacularScore?.toInt()}",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp
            )
        }

        TextButton(
            modifier = Modifier.weight(1f)
                .align(Alignment.CenterVertically),
            onClick = {
                clickFavorite.invoke(recipe)
            }
        ) {
            IconFavorite(
                recipe.favorite,
                modifier = Modifier
            ) {  }

            Text(
                text = "  ${stringResource(id = R.string.save)}",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp
            )
        }


    }
    Spacer(modifier = Modifier.height(10.dp))
}

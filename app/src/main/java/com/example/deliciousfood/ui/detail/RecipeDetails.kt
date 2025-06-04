

package com.example.deliciousfood.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.deliciousfood.domain.model.RecipesItem

@Composable
fun RecipeDetails(
    viewModel: RecipeDetailsViewModel,
    navController: NavController
) {
    val recipeDetails = viewModel.recipe.collectAsState()
     Body(recipe = recipeDetails.value , clickFavorite = viewModel::upsertFavorite,navController=navController)
}


@Composable
private fun Body(recipe: RecipesItem , navController: NavController,clickFavorite: (RecipesItem) -> Unit ){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            item { RecipesHeader(recipe , navController) }
            item { RecipeOptions(recipe, clickFavorite) }
            item { RecipeDivider() }
            item { RecipeSummary(recipe) }
            item { RecipeDivider() }
            item { RecipeTags(recipe) }
            item { RecipeCaloric(recipe) }
            item { RecipeDivider() }
            item { RecipeIngredientTitle() }
            items(recipe.ingredientOriginalString?: listOf()) { recipe ->
                RecipeIngredientItem(recipe)
            }
            item { RecipeDivider() }
            item { RecipeSteps(recipe.step) }
            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }
}

@Composable
private fun RecipeDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    )
}



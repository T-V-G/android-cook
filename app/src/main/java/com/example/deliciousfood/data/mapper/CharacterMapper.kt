
package com.example.deliciousfood.data.mapper


import com.example.deliciousfood.data.local.model.RecipeEntity
import com.example.deliciousfood.data.remote.model.Recipe
import com.example.deliciousfood.data.remote.model.createStepsList
import com.example.deliciousfood.data.remote.model.nutritionToNutrientsItem
import com.example.deliciousfood.data.remote.model.toIngredientUiModel
import com.example.deliciousfood.domain.model.RecipesItem

fun Recipe.toUiModel(): RecipesItem =
    RecipesItem(
        id,
        sustainable,
        glutenFree,
        veryPopular,
        healthScore,
        title,
        aggregateLikes,
        creditsText,
        readyInMinutes,
        dairyFree,
        vegetarian,
        image,
        veryHealthy,
        vegan,
        cheap,
        spoonacularScore,
        sourceName,
        nutrition?.caloricBreakdown?.percentCarbs,
        nutrition?.caloricBreakdown?.percentProtein,
        nutrition?.caloricBreakdown?.percentFat,
        nutrition?.nutrients?.nutritionToNutrientsItem()?.amount,
        nutrition?.nutrients?.nutritionToNutrientsItem()?.name,
        servings,
        createStepsList(analyzedInstructions),
        extendedIngredients?.toIngredientUiModel()
    )

fun RecipeEntity.toRecipesItem() = RecipesItem(
    id = id,
    title = title,
    image = image,
    readyInMinutes = readyInMinutes,
    sourceName = sourceName,
    favorite = favorite,
    ctime = ctime
)

fun RecipesItem.toRecipeEntity() = RecipeEntity(
    id = id,
    title = title,
    image = image,
    readyInMinutes = readyInMinutes,
    sourceName = sourceName,
)


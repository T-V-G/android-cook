
package com.example.deliciousfood.data.remote.model


data class Recipes(
    val recipes: List<Recipe> = emptyList()
)

data class Recipe(
    val sustainable: Boolean? = null,
    val glutenFree: Boolean? = null,
    val veryPopular: Boolean? = null,
    val healthScore: Double? = null,
    val title: String? = null,
    val aggregateLikes: Int? = null,
    val creditsText: String? = null,
    val readyInMinutes: Int? = null,
    val dairyFree: Boolean? = null,
    val vegetarian: Boolean? = null,
    val id: Int = -1,
    val image: String? = null,
    val veryHealthy: Boolean? = null,
    val vegan: Boolean? = null,
    val cheap: Boolean? = null,
    val spoonacularScore: Double? = null,
    val sourceName: String? = null,
    val nutrition: Nutrition? = null,
    val servings: Int? = 0,
    val analyzedInstructions: List<AnalyzedInstructionsItem>? = null,
    val extendedIngredients: List<ExtendedIngredientsItem>? = null
)

data class CaloricBreakdown(
    val percentCarbs: Double? = null,
    val percentProtein: Double? = null,
    val percentFat: Double? = null
)

data class NutrientsItem(
    val amount: Double? = null,
    val name: String? = null,
)

data class Nutrition(
    val caloricBreakdown: CaloricBreakdown? = null,
    val nutrients: List<NutrientsItem>? = null
)

data class SearchResponse(
    val results: List<Recipe> = emptyList(),
    val offset: Int? = null,
    val number: Int? = null,
    val totalResults: Int? = null
)


data class AnalyzedInstructionsItem(val steps: List<StepsItem>? = null)

data class StepsItem(val step: String? = null)

data class ExtendedIngredientsItem(val original: String? = null)

fun createStepsList(analyzedInstructions: List<AnalyzedInstructionsItem>?): List<String>? {
    return if (analyzedInstructions.isNullOrEmpty()) {
        emptyList()
    } else {
        analyzedInstructions[0].steps?.toStepsUiModel()
    }
}

fun List<NutrientsItem>.nutritionToNutrientsItem(): NutrientsItem? {
    return this.find {
        it.name?.equals("Calories", true) ?: false
    }
}

fun List<StepsItem>.toStepsUiModel(): List<String> {
    return this.map {
        it.step ?: ""
    }
}

fun List<ExtendedIngredientsItem>.toIngredientUiModel(): List<String> {
    return this.map {
        it.original ?: ""
    }
}




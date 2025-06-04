
package com.example.deliciousfood.domain.model

import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
data class RecipesItem(
    val id: Int = 1,
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
    val image: String? = null,
    val veryHealthy: Boolean? = null,
    val vegan: Boolean? = null,
    val cheap: Boolean? = null,
    val spoonacularScore: Double? = null,
    val sourceName: String? = null,
    val percentCarbs: Double? = null,
    val percentProtein: Double? = null,
    val percentFat: Double? = null,
    val nutrientsAmount: Double? = 0.0,
    val nutrientsName: String? = "",
    val servings: Int? = 0,
    val step: List<String>? = emptyList(),
    val ingredientOriginalString: List<String>? = emptyList(),
    val ratio: Float = 1f,
    val favorite: Boolean = false,
    val ctime: Date? = null,
)





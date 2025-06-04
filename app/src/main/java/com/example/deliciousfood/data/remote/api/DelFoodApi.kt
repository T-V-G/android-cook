
package com.example.deliciousfood.data.remote.api

import com.example.deliciousfood.data.remote.model.Recipe
import com.example.deliciousfood.data.remote.model.Recipes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DelFoodApi {

    companion object {
        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "a80b6b02aeb54c8ebdcc04433d707915"
    }

    @GET("/recipes/random")
    suspend fun getCharacters(
        @Query("apiKey") apiKey: String = API_KEY ,
        @Query("tags") tags: String? ,
        @Query("number") number: Int?
    ): Recipes

    @GET("/recipes/{id}/information")
    suspend fun getCharactersById(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("includeNutrition") includeNutrition: Boolean? = true
    ): Recipe

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("apiKey") apiKey: String = API_KEY ,
        @Query("query") query: String?,
        @Query("tags") tags: String?,
        @Query("number") number: Int?
    ): Recipe



}

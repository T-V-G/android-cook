
package com.example.deliciousfood.data.repository

import com.example.deliciousfood.data.local.db.AppDatabase
import com.example.deliciousfood.data.local.model.RecipeEntity
import com.example.deliciousfood.data.mapper.*
import com.example.deliciousfood.data.remote.api.DelFoodApi
import com.example.deliciousfood.domain.model.RecipesItem
import com.example.deliciousfood.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import retrofit2.http.Query

open class CharactersRepositoryImpl(private val api: DelFoodApi , private val db: AppDatabase) :
    CharactersRepository {

    override fun getCharacterList(): Flow<List<RecipesItem>> = flow { emit(api.getCharacters(tags="", number = 30).recipes) }
        .map { it.map { r -> r.toUiModel() } }

    override fun getFavoriteList(isAsc: Boolean): Flow<List<RecipesItem>> =
        db.characterDao().getFavorite(isAsc = isAsc)
            .map { it.map { i -> i.toRecipesItem() } }

    override fun getCharacterById(id: Int): Flow<RecipesItem> =
        flow { emit(api.getCharactersById(id)) }

            .map { it.toUiModel() }
            .combine(db.characterDao().getCharacter(id)) { res: RecipesItem , entity: RecipeEntity? ->
                res.copy(favorite = entity?.favorite ?: false)
            }

    override fun addFavoriteStatus(listCharacter: List<RecipesItem>): Flow<List<RecipesItem>> =
        flowOf(listCharacter)
            .combine(db.characterDao().getAll()) { list: List<RecipesItem> , db: List<RecipeEntity> ->
                list.toMutableList().map {
                    it.copy(favorite = db.find { i -> it.id == i.id }?.favorite ?: false)
                }
            }

    override fun updateFavorite(recipe: RecipesItem): Flow<Boolean> = flowOf(recipe)
        .map { it.toRecipeEntity().copy(favorite = !recipe.favorite) }
        .map { db.characterDao().insert(it) }
        .map { it != 0L }

    override fun searchRecipes(query: String): Flow<List<RecipesItem>> = flow {
        emit(api.searchRecipes(query = query, tags = "", number = 30).results)
    }.map { recipes -> recipes.map { it.toUiModel() } }
}

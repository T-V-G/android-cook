
package com.example.deliciousfood.domain.repository


import com.example.deliciousfood.domain.model.RecipesItem
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharacterList(): Flow<List<RecipesItem>>
    fun getFavoriteList(isAsc: Boolean = false): Flow<List<RecipesItem>>
    fun getCharacterById(id: Int): Flow<RecipesItem>
    fun addFavoriteStatus(listCharacter: List<RecipesItem>): Flow<List<RecipesItem>>
    fun updateFavorite(recipe: RecipesItem): Flow<Boolean>
}

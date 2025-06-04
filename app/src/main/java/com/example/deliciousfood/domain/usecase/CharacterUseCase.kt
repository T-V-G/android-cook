
package com.example.deliciousfood.domain.usecase


import com.example.deliciousfood.domain.model.RecipesItem
import com.example.deliciousfood.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow

class GetCharacterListUseCase(private val repo: CharactersRepository) {
    fun execute(): Flow<List<RecipesItem>> = repo.getCharacterList()
}

class GetFavoriteListUseCase(private val repo: CharactersRepository) {
    fun execute(isAsc: Boolean = false): Flow<List<RecipesItem>> = repo.getFavoriteList(isAsc)
}

class GetCharacterUseCase(private val repo: CharactersRepository) {
    fun execute(id: Int): Flow<RecipesItem> = repo.getCharacterById(id)
}

class AddFavoriteToListUseCase(private val repo: CharactersRepository) {
    fun execute(list: List<RecipesItem>): Flow<List<RecipesItem>> = repo.addFavoriteStatus(list)
}

class UpdateFavoriteUseCase(private val repo: CharactersRepository) {
    fun execute(recipe: RecipesItem): Flow<Boolean> = repo.updateFavorite(recipe)
}

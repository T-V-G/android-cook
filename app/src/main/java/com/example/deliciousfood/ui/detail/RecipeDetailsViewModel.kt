
package com.example.deliciousfood.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.deliciousfood.domain.model.RecipesItem
import com.example.deliciousfood.domain.usecase.GetCharacterUseCase
import com.example.deliciousfood.domain.usecase.UpdateFavoriteUseCase
import com.example.deliciousfood.ui.BaseFavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val getCharacterUseCase: GetCharacterUseCase,
    updateFavoriteUseCase: UpdateFavoriteUseCase,
) : BaseFavoriteViewModel(updateFavoriteUseCase) {

    private val _recipe = MutableStateFlow(RecipesItem())
    val recipe: StateFlow<RecipesItem> get() = _recipe.asStateFlow()

    init {
        val id = handle.get<Int>("id") ?: throw Exception("Error")
        getInfo(id)
    }

    private fun getInfo(id: Int) {
        getCharacterUseCase.execute(id)
            .onEach {
                    _recipe.value = it
            }
            .flowOn(Dispatchers.IO)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }
}

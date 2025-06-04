package com.example.deliciousfood.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliciousfood.domain.model.RecipesItem
import com.example.deliciousfood.domain.usecase.UpdateFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn

open class BaseViewModel : ViewModel()

open class BaseFavoriteViewModel(private val updateFavoriteUseCase: UpdateFavoriteUseCase) :
    BaseViewModel() {

    fun upsertFavorite(recipe: RecipesItem) {
        updateFavoriteUseCase.execute(recipe)
            .flowOn(Dispatchers.IO)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }

}

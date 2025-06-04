
package com.example.deliciousfood.ui.favorite

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.deliciousfood.domain.model.RecipesItem
import com.example.deliciousfood.domain.usecase.GetFavoriteListUseCase
import com.example.deliciousfood.domain.usecase.UpdateFavoriteUseCase
import com.example.deliciousfood.ui.BaseFavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
@Inject constructor(
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    updateFavoriteUseCase: UpdateFavoriteUseCase,
) : BaseFavoriteViewModel(updateFavoriteUseCase) {

    private val _list = MutableStateFlow(emptyList<RecipesItem>())
    val list get() = _list.asStateFlow()

    init {
        getFavoriteList()
    }

    private fun getFavoriteList(isAsc: Boolean = false) {
        getFavoriteListUseCase.execute(isAsc)
            .map { _list.value = it }
            .flowOn(Dispatchers.IO)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }
}

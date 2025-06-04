
package com.example.deliciousfood.ui.list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel


import com.example.deliciousfood.domain.model.RecipesItem
import com.example.deliciousfood.domain.usecase.AddFavoriteToListUseCase
import com.example.deliciousfood.domain.usecase.GetCharacterListUseCase
import com.example.deliciousfood.domain.usecase.UpdateFavoriteUseCase
import com.example.deliciousfood.ui.BaseFavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class ListViewModel
@Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val addFavoriteToListUseCase: AddFavoriteToListUseCase,
    updateFavoriteUseCase: UpdateFavoriteUseCase,
) : BaseFavoriteViewModel(updateFavoriteUseCase) {

    private val _list = MutableStateFlow(listOf<RecipesItem>())
    val list: StateFlow<List<RecipesItem>> get() = _list

    init {
        getCharacterList()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCharacterList() {
        getCharacterListUseCase.execute()
            .onEach { _list.value = it }
            .flatMapConcat { addFavoriteToListUseCase.execute(it ) }
            .onEach { _list.value = it }
            .flowOn(Dispatchers.IO)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }
}

package com.example.deliciousfood.ui.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.deliciousfood.domain.model.RecipesItem
import com.example.deliciousfood.domain.usecase.SearchRecipesUseCase
import com.example.deliciousfood.domain.usecase.AddFavoriteToListUseCase
import com.example.deliciousfood.domain.usecase.UpdateFavoriteUseCase
import com.example.deliciousfood.ui.BaseFavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRecipesUseCase: SearchRecipesUseCase,
    private val addFavoriteToListUseCase: AddFavoriteToListUseCase,
    updateFavoriteUseCase: UpdateFavoriteUseCase,
) : BaseFavoriteViewModel(updateFavoriteUseCase) {

    private val _searchResults = MutableStateFlow(listOf<RecipesItem>())
    val searchResults: StateFlow<List<RecipesItem>> get() = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun searchRecipes(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            searchRecipesUseCase.execute(query)
                .flatMapConcat { addFavoriteToListUseCase.execute(it) }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    e.printStackTrace()
                    _isLoading.value = false
                }
                .collect { recipes ->
                    _searchResults.value = recipes
                    _isLoading.value = false
                }
        }
    }
}
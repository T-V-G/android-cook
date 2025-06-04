
package com.example.deliciousfood.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import com.example.deliciousfood.domain.repository.CharactersRepository
import com.example.deliciousfood.domain.usecase.AddFavoriteToListUseCase
import com.example.deliciousfood.domain.usecase.GetCharacterListUseCase
import com.example.deliciousfood.domain.usecase.GetCharacterUseCase
import com.example.deliciousfood.domain.usecase.GetFavoriteListUseCase
import com.example.deliciousfood.domain.usecase.SearchRecipesUseCase
import com.example.deliciousfood.domain.usecase.UpdateFavoriteUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetCharacterListUseCase(repo: CharactersRepository) = GetCharacterListUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideGetFavoriteListUseCase(repo: CharactersRepository) = GetFavoriteListUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideGetCharacterUseCase(repo: CharactersRepository) = GetCharacterUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideAddFavoriteToListUseCase(repo: CharactersRepository) = AddFavoriteToListUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideUpdateFavoriteUseCase(repo: CharactersRepository) = UpdateFavoriteUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideSearchRecipesUseCase(repo: CharactersRepository) = SearchRecipesUseCase(repo)
}

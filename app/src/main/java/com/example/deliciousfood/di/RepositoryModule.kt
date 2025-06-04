
package com.example.deliciousfood.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.deliciousfood.data.local.db.AppDatabase
import com.example.deliciousfood.data.remote.api.DelFoodApi
import com.example.deliciousfood.data.repository.CharactersRepositoryImpl
import com.example.deliciousfood.domain.repository.CharactersRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharactersRepository(api: DelFoodApi , db: AppDatabase): CharactersRepository =
        CharactersRepositoryImpl(api, db)
}

package com.studyProject.rickandmorty.di

import com.studyProject.rickandmorty.data.repository.CharacterRepositoryImpl
import com.studyProject.rickandmorty.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// @Binds amarra a INTERFACE à IMPLEMENTAÇÃO
// Precisa ser abstract class porque @Binds não tem corpo.
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        impl: CharacterRepositoryImpl
    ): CharacterRepository
}

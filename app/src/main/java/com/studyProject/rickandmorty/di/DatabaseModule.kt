package com.studyProject.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.studyProject.rickandmorty.data.local.FavoriteCharacterDao
import com.studyProject.rickandmorty.data.local.RickAndMortyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "rick_and_morty.db"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RickAndMortyDatabase =
        Room.databaseBuilder(context, RickAndMortyDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideFavoriteCharacterDao(database: RickAndMortyDatabase): FavoriteCharacterDao =
        database.favoriteCharacterDao()
}

package com.studyProject.rickandmorty.di

import com.studyProject.rickandmorty.BuildConfig
import com.studyProject.rickandmorty.data.remote.RickAndMortyApi
import com.studyProject.rickandmorty.data.repository.CharacterRepositoryImpl
import com.studyProject.rickandmorty.domain.repository.CharacterRepository
import com.studyProject.rickandmorty.ui.character.CharacterViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val BASE_URL = "https://rickandmortyapi.com/api/"

// O módulo é a "receita" de como o Koin constrói cada dependência.
// Substitui o antigo RetrofitClient + o .shared do repository.
val appModule = module {

    // single = UMA instância compartilhada no app (o antigo "@Singleton").
    single {
        Json { ignoreUnknownKeys = true }
    }

    single {
        OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get()) // get() = pega o OkHttpClient que o Koin já sabe criar
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    // provê a api a partir do Retrofit
    single { get<Retrofit>().create(RickAndMortyApi::class.java) }

    // amarra a INTERFACE (CharacterRepository) à IMPLEMENTAÇÃO (CharacterRepositoryImpl).
    // o <CharacterRepository> registra sob o tipo da interface — como o @Binds do Hilt.
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }

    // viewModel { } = registro especial p/ ViewModels; injeta o repository
    viewModel { CharacterViewModel(get()) }
}

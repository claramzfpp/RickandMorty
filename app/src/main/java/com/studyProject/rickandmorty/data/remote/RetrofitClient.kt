package com.studyProject.rickandmorty.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

// `object` = singleton do Kotlin
// equivale a um `static let shared
object RetrofitClient {

    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val json = Json {
        ignoreUnknownKeys = true // defini para ignorar os campos que vieram do JSON que não modelamos
    }

    private val okHttpClient = OkHttpClient.Builder() //como se fosse o URLSession
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build() //Builder pattern

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) //aqui eu junto toda a url
        .client(okHttpClient) //definindo qual "URLSession" usar de referência
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())) //é um JSONDecoder
        .build()

    //basicamente juntando nossa interface RickAndMortyApi + "delegates" para o client conseguir fazer a chamada de API
    val api: RickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)
}

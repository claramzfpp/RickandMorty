package com.studyProject.rickandmorty

import android.app.Application
import com.studyProject.rickandmorty.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

// Classe Application: roda uma vez quando o app sobe.
// É aqui que LIGAMOS o Koin e passamos o módulo com as "receitas".
class RickAndMortyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RickAndMortyApp)
            modules(appModule)
        }
    }
}

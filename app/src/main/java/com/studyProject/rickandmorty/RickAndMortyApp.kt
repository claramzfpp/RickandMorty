package com.studyProject.rickandmorty

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// @HiltAndroidApp liga o Hilt no app e gera o container de dependências.
// Substitui o startKoin { } que tínhamos aqui.
@HiltAndroidApp
class RickAndMortyApp : Application()

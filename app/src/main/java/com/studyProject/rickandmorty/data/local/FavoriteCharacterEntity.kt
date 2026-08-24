package com.studyProject.rickandmorty.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_characters") //basicamente qual local os dados serão alocados
data class FavoriteCharacterEntity(
    /*
    para simplificar as coisas, vamos apenas salvar o id do character e
    fazer um fetch dessas informações

    como é um projeto de estudo, nosso objetivo é apenas aprender como a persistência funciona
     */
    @PrimaryKey val characterId: Int, //chave : valor
)

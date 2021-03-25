package com.mariusbudin.sample.features.characters.data.remote

import com.mariusbudin.sample.features.characters.data.model.remote.CharacterRemoteModel
import com.mariusbudin.sample.features.characters.data.model.remote.CharactersListRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int = 1): CharactersListRemoteModel

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterRemoteModel
}
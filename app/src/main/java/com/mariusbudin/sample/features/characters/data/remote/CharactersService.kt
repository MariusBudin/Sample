package com.mariusbudin.sample.features.characters.data.remote

import com.mariusbudin.sample.features.characters.data.model.remote.CharacterRemoteModel
import com.mariusbudin.sample.features.characters.data.model.remote.CharactersListRemoteModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersService {
    @GET("character")
    suspend fun getCharacters(): Response<CharactersListRemoteModel>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<CharacterRemoteModel>
}
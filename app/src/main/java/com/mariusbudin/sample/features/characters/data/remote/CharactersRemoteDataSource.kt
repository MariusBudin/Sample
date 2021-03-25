package com.mariusbudin.sample.features.characters.data.remote

import com.mariusbudin.sample.features.characters.data.model.remote.CharactersListRemoteModel
import javax.inject.Inject

class CharactersRemoteDataSource @Inject constructor(
    private val service: CharactersService
) {

    suspend fun getCharacters(page: Int): CharactersListRemoteModel = service.getCharacters(page)
    suspend fun getCharacter(id: Int) = service.getCharacter(id)
}
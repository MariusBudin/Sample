package com.mariusbudin.sample.features.characters.data.remote

import com.mariusbudin.sample.core.data.remote.BaseDataSource
import javax.inject.Inject

class CharactersRemoteDataSource @Inject constructor(
    private val service: CharactersService
) : BaseDataSource() {

    suspend fun getCharacters() = getResult { service.getCharacters() }
    suspend fun getCharacter(id: Int) = getResult { service.getCharacter(id) }
}
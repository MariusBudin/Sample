package com.mariusbudin.sample.features.characters.data.repository

import com.mariusbudin.sample.core.data.performGetOperation
import com.mariusbudin.sample.features.characters.data.local.CharacterDao
import com.mariusbudin.sample.features.characters.data.model.Character
import com.mariusbudin.sample.features.characters.data.remote.CharactersRemoteDataSource
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val localDataSource: CharacterDao
) {

    fun getCharacter(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getCharacter(id) },
        networkCall = { remoteDataSource.getCharacter(id) },
        saveNetworkCallResult = { localDataSource.insert(Character.mapFromRemoteModel(it)) }
    )

    fun getCharacters() = performGetOperation(
        databaseQuery = { localDataSource.getCharacters() },
        networkCall = { remoteDataSource.getCharacters() },
        saveNetworkCallResult = {
            localDataSource.insertAll(it.results.map { result ->
                Character.mapFromRemoteModel(result)
            })
        }
    )
}
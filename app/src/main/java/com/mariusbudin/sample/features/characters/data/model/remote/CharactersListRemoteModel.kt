package com.mariusbudin.sample.features.characters.data.model.remote

data class CharactersListRemoteModel(
    val info: InfoRemoteModel,
    val results: List<CharacterRemoteModel>
)
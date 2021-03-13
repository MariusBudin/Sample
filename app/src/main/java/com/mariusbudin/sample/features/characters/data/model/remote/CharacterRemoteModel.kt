package com.mariusbudin.sample.features.characters.data.model.remote

data class CharacterRemoteModel(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val location: LocationRemoteModel,
    val image: String
)

enum class Status { Alive, Dead, unknown }
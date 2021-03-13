package com.mariusbudin.sample.features.characters.data.model.remote

data class InfoRemoteModel(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?,
)
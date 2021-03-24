package com.mariusbudin.sample.features.characters.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.paging.ExperimentalPagingApi
import com.mariusbudin.sample.features.characters.data.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()
    private val _character = _id.switchMap { repository.getCharacter(it) }

    val character = _character.distinctUntilChanged()

    fun init(id: Int) {
        _id.value = id
    }
}
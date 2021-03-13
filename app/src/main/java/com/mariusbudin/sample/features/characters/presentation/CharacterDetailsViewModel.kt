package com.mariusbudin.sample.features.characters.presentation

import androidx.lifecycle.*
import com.mariusbudin.sample.core.platform.Resource
import com.mariusbudin.sample.features.characters.data.model.Character
import com.mariusbudin.sample.features.characters.data.repository.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()
    private val _character = _id.switchMap { id ->
        repository.getCharacter(id)
    }

    val character: LiveData<Resource<Character>> = _character.distinctUntilChanged()

    fun init(id: Int) {
        _id.value = id
    }
}
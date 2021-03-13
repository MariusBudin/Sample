package com.mariusbudin.sample.features.characters.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.mariusbudin.sample.features.characters.data.repository.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    repository: CharactersRepository
) : ViewModel(), LifecycleObserver {

    val characters = repository.getCharacters().distinctUntilChanged()
}

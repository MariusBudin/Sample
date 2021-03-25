package com.mariusbudin.sample.features.characters.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.mariusbudin.sample.features.characters.data.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class CharactersViewModel @Inject constructor(
    repository: CharactersRepository
) : ViewModel(), LifecycleObserver {

    @ExperimentalPagingApi
    val characters = repository.getCharacters().distinctUntilChanged().cachedIn(viewModelScope)
}

package com.mariusbudin.sample.features.characters.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.mariusbudin.sample.features.characters.data.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class CharactersViewModel @Inject constructor(
    repository: CharactersRepository
) : ViewModel(), LifecycleObserver {

    @ExperimentalPagingApi
    val characters = repository.getCharacters().distinctUntilChanged()
}

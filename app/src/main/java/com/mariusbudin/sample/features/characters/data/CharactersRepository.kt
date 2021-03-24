package com.mariusbudin.sample.features.characters.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mariusbudin.sample.core.data.local.AppDatabase
import com.mariusbudin.sample.features.characters.data.remote.CharactersRemoteDataSource
import javax.inject.Inject

/**
 * Repository implementation that uses a database backed [androidx.paging.PagingSource] and
 * [androidx.paging.RemoteMediator] to load pages from network when there are no more items cached
 * in the database to load.
 */
@ExperimentalPagingApi
class CharactersRepository @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val db: AppDatabase
) {

    fun getCharacter(id: Int) = db.characterDao().getCharacter(id)

    fun getCharacters() = Pager(
        config = PagingConfig(PAGE_SIZE),
        remoteMediator = PageKeyedRemoteMediator(remoteDataSource, db)
    ) {
        db.characterDao().getCharacters()
    }.liveData


    companion object {
        const val PAGE_SIZE = 10
    }
}
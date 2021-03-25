package com.mariusbudin.sample.features.characters.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mariusbudin.sample.core.data.local.AppDatabase

import com.mariusbudin.sample.features.characters.data.local.CharacterDao
import com.mariusbudin.sample.features.characters.data.local.RemoteKeyDao
import com.mariusbudin.sample.features.characters.data.model.Character
import com.mariusbudin.sample.features.characters.data.model.RemoteKey
import com.mariusbudin.sample.features.characters.data.model.remote.CharactersListRemoteModel
import com.mariusbudin.sample.features.characters.data.remote.CharactersRemoteDataSource
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PageKeyedRemoteMediator(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val db: AppDatabase
) : RemoteMediator<Int, Character>() {
    private val characterDao: CharacterDao = db.characterDao()
    private val remoteKeyDao: RemoteKeyDao = db.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        try {
            // Get the closest item from PagingState that we want to load data around.
            val loadKey = when (loadType) {
                REFRESH -> null
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    // Query DB for key.
                    // RemoteKey is a wrapper object we use to keep track of page keys we
                    // receive from the API to fetch the next or previous page.
                    val remoteKey = db.withTransaction {
                        remoteKeyDao.remoteKey()
                    }

                    // We must explicitly check if the page key is null when appending, since the
                    // API informs the end of the list by returning null for page key, but
                    // passing a null key to API will fetch the initial page.
                    if (remoteKey.nextPageKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextPageKey
                }
            }
            val data: CharactersListRemoteModel =
                remoteDataSource.getCharacters(page = loadKey.getPageFromKey() ?: 1)
            val items: List<Character> = data.results.map { Character.mapFromRemoteModel(it) }
            val next: String? = data.info.next

            db.withTransaction {
                if (loadType == REFRESH) {
                    characterDao.deleteAll()
                    remoteKeyDao.deleteKey()
                }

                remoteKeyDao.insert(RemoteKey(nextPageKey = next))
                characterDao.insertAll(items)
            }

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    companion object {

        //quick and dirty workaround for getting an int from the next page indicator.
        //TODO replace this for a production app
        fun String?.getPageFromKey() = this?.removeRange(0, indexOf("page=") + 5)?.toIntOrNull()
    }
}

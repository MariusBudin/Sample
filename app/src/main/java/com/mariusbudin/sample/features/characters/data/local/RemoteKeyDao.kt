package com.mariusbudin.sample.features.characters.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mariusbudin.sample.features.characters.data.model.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: RemoteKey)

    @Query("SELECT * FROM remote_keys")
    suspend fun remoteKey(): RemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun deleteKey()
}
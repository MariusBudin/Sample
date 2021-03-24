package com.mariusbudin.sample.features.characters.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mariusbudin.sample.features.characters.data.model.Character

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, Character>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: Int): LiveData<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Query("DELETE FROM characters")
    suspend fun deleteAll()
}
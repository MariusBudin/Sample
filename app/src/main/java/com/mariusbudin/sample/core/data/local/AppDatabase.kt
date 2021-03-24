package com.mariusbudin.sample.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mariusbudin.sample.features.characters.data.local.CharacterDao
import com.mariusbudin.sample.features.characters.data.local.RemoteKeyDao
import com.mariusbudin.sample.features.characters.data.model.Character
import com.mariusbudin.sample.features.characters.data.model.RemoteKey

@Database(
    entities = [Character::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {

        private const val DB_NAME = "characters_db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

}
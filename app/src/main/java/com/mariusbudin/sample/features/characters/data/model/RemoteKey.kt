package com.mariusbudin.sample.features.characters.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mariusbudin.sample.features.characters.data.model.RemoteKey.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RemoteKey(
    @PrimaryKey
    val keyId: Int = 0,
    val nextPageKey: String?
) {
    companion object {
        const val TABLE_NAME = "remote_keys"
    }
}

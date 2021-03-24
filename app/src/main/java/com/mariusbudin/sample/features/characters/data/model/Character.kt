package com.mariusbudin.sample.features.characters.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mariusbudin.sample.features.characters.data.model.Character.Companion.TABLE_NAME
import com.mariusbudin.sample.features.characters.data.model.remote.CharacterRemoteModel
import java.util.*

@Entity(tableName = TABLE_NAME)
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val location: String,
    val image: String
) {
    companion object {
        const val TABLE_NAME = "characters"

        var DIFF_CALLBACK: DiffUtil.ItemCallback<Character> =
            object : DiffUtil.ItemCallback<Character>() {
                override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                    return oldItem == newItem
                }
            }

        fun mapFromRemoteModel(remoteModel: CharacterRemoteModel) =
            Character(
                id = remoteModel.id,
                name = remoteModel.name,
                status = remoteModel.status.name.toUpperCase(Locale.getDefault()),
                species = remoteModel.species,
                location = remoteModel.location.name,
                image = remoteModel.image
            )
    }
}
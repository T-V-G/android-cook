
package com.example.deliciousfood.data.local.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

import com.example.deliciousfood.data.local.model.RecipeEntity.Companion.TABLE_NAME
import java.util.Date

@Immutable
@Entity(tableName = TABLE_NAME, indices = [(Index(value = ["id"], unique = true))])
data class RecipeEntity(
    @PrimaryKey
    val id: Int,
    val title: String? = "",
    val image: String? = "",
    val readyInMinutes: Int? = 0,
    val sourceName: String? = "",
    val favorite: Boolean = false,
    var ctime: Date = Date(),
) {
    companion object {
        const val TABLE_NAME = "recipe"
    }
}

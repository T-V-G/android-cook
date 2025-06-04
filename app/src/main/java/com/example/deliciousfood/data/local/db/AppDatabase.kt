
package com.example.deliciousfood.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.deliciousfood.data.local.db.AppDatabase.Companion.DB_VERSION
import com.example.deliciousfood.data.local.model.RecipeEntity

@Database(
    entities = [
        RecipeEntity::class
    ],
    version = DB_VERSION,
    exportSchema = false
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_VERSION = 1
        const val NAME = "app.db"
    }

    abstract fun characterDao(): CharacterDao
}


package com.example.deliciousfood.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deliciousfood.data.local.model.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RecipeEntity): Long

    @Query("SELECT * FROM ${RecipeEntity.TABLE_NAME}")
    fun getAll(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM ${RecipeEntity.TABLE_NAME} t WHERE t.id=:id ORDER BY t.ctime")
    fun getCharacter(id: Int): Flow<RecipeEntity?>

    @Query(
        "SELECT * FROM ${RecipeEntity.TABLE_NAME} t WHERE t.favorite = 1 ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN t.ctime END ASC," +
            "CASE WHEN :isAsc = 0 THEN t.ctime END DESC"
    )

    fun getFavorite(isAsc: Boolean = true): Flow<List<RecipeEntity>>

    @Delete
    suspend fun delete(entity: RecipeEntity): Int
}

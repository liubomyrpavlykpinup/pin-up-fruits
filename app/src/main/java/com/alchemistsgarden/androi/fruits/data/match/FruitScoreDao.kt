package com.alchemistsgarden.androi.fruits.data.match

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitScoreDao {

    @Query("SELECT * FROM fruitscore")
    fun getAll(): Flow<List<FruitScore>>

    @Query("DELETE FROM fruitscore")
    suspend fun clear()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fruitScore: FruitScore)
}
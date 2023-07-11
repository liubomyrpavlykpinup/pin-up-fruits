package com.turbo.star.fruits.data.match

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FruitScore::class], version = 1, exportSchema = false)
abstract class FruitsDatabase : RoomDatabase() {

    abstract fun fruitScoreDao(): FruitScoreDao

    companion object {

        private var INSTANCE: FruitsDatabase? = null

        fun instance(context: Context) =
            INSTANCE ?: Room.databaseBuilder(context, FruitsDatabase::class.java, "fruit-scores")
                .build().also {
                    INSTANCE = it
                }
    }
}
package com.turbo.star.fruits.data.match

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FruitScore(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val count: Int,
    val score: Double,
    val imageRes: Int
)
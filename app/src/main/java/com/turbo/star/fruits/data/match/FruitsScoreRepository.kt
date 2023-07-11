package com.turbo.star.fruits.data.match

import kotlinx.coroutines.flow.Flow

interface FruitsScoreRepository {

    suspend fun save(score: FruitScore)

    fun getAll(): Flow<List<FruitScore>>

    suspend fun clear()
}
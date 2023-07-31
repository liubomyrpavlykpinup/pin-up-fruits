package com.alchemistsgarden.androi.fruits.data.match

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class LocalFruitsScoreRepository(
    private val fruitScoreDao: FruitScoreDao
) : FruitsScoreRepository {
    override suspend fun save(score: FruitScore) {
        withContext(Dispatchers.IO) {
            fruitScoreDao.insert(score)
        }
    }

    override fun getAll(): Flow<List<FruitScore>> {
        return fruitScoreDao.getAll().flowOn(Dispatchers.IO)
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            fruitScoreDao.clear()
        }
    }
}
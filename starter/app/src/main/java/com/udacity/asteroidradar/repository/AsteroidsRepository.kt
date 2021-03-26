package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.DatabaseAsteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidsRepository(private val database: AsteroidDatabase) {


    val asteroids = database.asteroidDao.getAsteroids()

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroids = Network.asteroids.getAsteroids().await()
            database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
        }
    }


}
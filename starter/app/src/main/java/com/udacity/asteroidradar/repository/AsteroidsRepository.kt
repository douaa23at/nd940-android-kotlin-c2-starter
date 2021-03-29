package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.utils.getToday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository(private val database: AsteroidDatabase) {


    val asteroids: LiveData<List<Asteroid>> = database.asteroidDao.getAsteroids(getToday())

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroids = Network.asteroids.getAsteroids()
            val asteroidsArray = parseAsteroidsJsonResult(JSONObject(asteroids))
            database.asteroidDao.insertAll(*asteroidsArray.toTypedArray())
        }
    }
}

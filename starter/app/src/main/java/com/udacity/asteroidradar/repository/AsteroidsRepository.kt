package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.asDatabasePictureOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.utils.getToday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

class AsteroidsRepository(private val database: AsteroidDatabase) {


    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids(getToday())) {
            it.asDomainModel()
        }

    val pictureOfDay: LiveData<PictureOfDay> = database.asteroidDao.getPictureOfDay()

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroids = Network.asteroids.getAsteroids(API_KEY).await()
                val asteroidsArray = parseAsteroidsJsonResult(JSONObject(asteroids))
                    .map { it.asDatabaseModel() }
                database.asteroidDao.insertAll(*asteroidsArray.toTypedArray())
            } catch (e: Exception) {
                Log.i("exception", "${e.message}")
            }

        }
    }

    suspend fun refreshPictureOfTheDay() {
        withContext(Dispatchers.IO) {
            try {
                val pictureOfDay =
                    Network.asteroids.getPictureOfTheDay(API_KEY).await().asDatabasePictureOfDay()
                val mediaType = pictureOfDay.mediaType
                if (mediaType == "image") {
                    database.asteroidDao.insertPictureOfTheDay(pictureOfDay)
                } else {
                    Log.i("PictureOfTheDay", " mediatype is a video")
                }

            } catch (e: Exception) {
                Log.i("exception", "${e.message}")
            }

        }
    }

}

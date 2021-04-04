package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import com.udacity.asteroidradar.utils.Event
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repository = AsteroidsRepository(database)
    val asteroids = repository.asteroids
    val pictureOfDay = repository.pictureOfDay
    val navigateToDetailScreen = MutableLiveData<Event<Asteroid>>()

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
            repository.refreshPictureOfTheDay()
        }
    }

    fun navigateToDetailPage(asteroid: Asteroid) {
        navigateToDetailScreen.value = Event(asteroid)
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
package com.udacity.asteroidradar

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.random.Random

@Entity
data class PictureOfDay(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class NetworkPictureOfDay(
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String
)

fun NetworkPictureOfDay.asDatabasePictureOfDay(): PictureOfDay {

    return PictureOfDay(
        id = Random.nextLong(),
        title = this.title,
        mediaType = this.mediaType,
        url = this.url
    )
}
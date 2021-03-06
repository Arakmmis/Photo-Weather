package com.example.photoweather.data.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photoweather.data.cache.constants.PhotoConstants
import java.io.Serializable
import java.util.*
import kotlin.random.Random

@Entity(tableName = PhotoConstants.TABLE_NAME)
data class Photo(
    @PrimaryKey
    var id: String = Random.nextInt().toString(),
    var imagePath: String = "",
    var date: Date = Date(),
    var temp: Int = Random.nextInt(-20, 50),
    var tempIconUrl: String = "",
    var tempDesc: String = "",
    var city: String = "",
    var country: String = ""
): Serializable {

    override fun equals(other: Any?): Boolean {
        return this.id == (other as Photo?)?.id
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

package data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val description: String?,
    val icon: String?,
    val id: Int,
)
package data.remote.dto

import kotlinx.serialization.SerialName

data class Main(
    @SerialName("temp")
    val temperature: Double
)
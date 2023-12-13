package io.huvz.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File

@Serializable
data class DeMessage (
    @SerialName("name")
    val name:String,
)


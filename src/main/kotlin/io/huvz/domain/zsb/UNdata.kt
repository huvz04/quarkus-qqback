package io.huvz.domain.zsb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(
    val success: Boolean,
    @SerialName("data")
    val data: Data
)

@Serializable
data class Data(
    @SerialName("universityVos")
    val universities: List<University>,
    @SerialName("names")
    val names:List<String>,
)
package io.huvz.domain.zsb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(
    val success: Boolean,
    val messageCode: String,
    val message: String?,
    val data: Data
)

@Serializable
data class Data(
    @SerialName("universityVos")
    val universities: List<University>,
)
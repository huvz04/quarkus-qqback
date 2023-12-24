package io.huvz.domain.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassInfo(
    @SerialName("bj")
    val bj : String,
    @SerialName("zyh_id")
    val zyh_id : String,
    @SerialName("bh_id")
    val bh_id : String,
    var nj_id :String
)
package io.huvz.domain.vo

import io.huvz.client.MyClass
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MyClassList(
    @SerialName("kblx") var kblx :Long,
    @SerialName("sfxsd") var sfxsd  : String?="",
    @SerialName("kbList") var kbList :List<MyClass>  = listOf<MyClass>()
)
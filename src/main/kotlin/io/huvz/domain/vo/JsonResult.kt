package io.huvz.domain.vo

@kotlinx.serialization.Serializable
data class JsonResult(
    val classes: List<ClassInfo>
)
package io.huvz.domain.zsb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class University (
    @SerialName("name") var name: String,
    /**
     * 搜索使用 universityName  查询名字
     */
    @SerialName("universityDtoList") var universityDtoList: List<UniversityMajor>,
)

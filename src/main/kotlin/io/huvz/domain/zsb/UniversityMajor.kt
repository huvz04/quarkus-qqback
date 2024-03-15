package io.huvz.domain.zsb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UniversityMajor(
    //专业名称
    @SerialName("undergraduate_name") var undergraduateName: String,
    //类别
    @SerialName("category") var category: String,
    //学费
    @SerialName("tuition") var tuition: Double,
    //计划招生数
    @SerialName("plannedNumber") var plannedNumber: Long,
    //去年分数线
    @SerialName("fractional_line") var fractionalLine: Long? = 0,
)

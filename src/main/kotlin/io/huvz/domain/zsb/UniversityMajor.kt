package io.huvz.domain.zsb

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


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
    @Serializable(with = NullableLongAsStringSerializer::class)
    @SerialName("fractional_line")
    var fractionalLine: Long? = 0,
)


@Serializer(forClass = Long::class)
object NullableLongAsStringSerializer : KSerializer<Long?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("NullableLongAsStringSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Long?) {
        value?.toString()?.let { encoder.encodeString(it) }
    }

    override fun deserialize(decoder: Decoder): Long? {
        val value = decoder.decodeString()
        return try {
            value.toLong()
        } catch (e: NumberFormatException) {
            0L // 返回 null，跳过无法转换为 Long 的字符串
        }
    }
}
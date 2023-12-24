package io.huvz.domain.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyClass(
    //@SerialName("qtkcgs") var qtkcgs: String="",
    @SerialName("cd_id") var cdId : String="", // 场地ID
    @SerialName("cdmc")var cdmc : String="",  // 场地名称
    @SerialName("jc") var jc : String ="", // 节次
    @SerialName("jcs") var jcs: String="", // 节次数
    @SerialName("kcmc") var kcmc: String="", // 课程名称
    @SerialName("rsdzjs") var rsdzjs: Int = 0, // 任课教师数
    @SerialName("xf")  var xf: String="", // 学分
    @SerialName("xm")  var xm: String="", // 姓名
    @SerialName("xnm")  var xnm: String="", // 学年码
    @SerialName("xqj")  var xqj: String="", // 星期几
    @SerialName("xqjmc")  var xqjmc: String="", // 星期几名称
    @SerialName("year")  var year: String="", // 年份
    @SerialName("zcd")  var zcd: String="", // 周次段

)

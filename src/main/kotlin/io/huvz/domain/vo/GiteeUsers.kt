package io.huvz.domain.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class GiteeUsers(
    @SerialName("login") var login:String,
    @SerialName("name") var name:String,
    @SerialName("avatar_url") var avatarUrl:String,
    @SerialName("html_url") var url:String,

)


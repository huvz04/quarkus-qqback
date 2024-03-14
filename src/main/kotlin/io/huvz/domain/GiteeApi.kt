package io.huvz.domain

enum class GiteeApi(val url: String) {
    SEARCH_USER("https://gitee.com/api/v5/search/users"),
    SEARCH_REPOSITORIES("https://gitee.com/api/v5/search/repositories"),
    SEARCH_SYZSB("https://ycqsdbz.cn/young/fy/applet/professional/universityV2"),
}
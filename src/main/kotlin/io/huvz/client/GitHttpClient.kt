package io.huvz.client

import io.huvz.config.MyAppConfig
import io.huvz.domain.GiteeApi
import okhttp3.*
import java.io.IOException


class GitHttpClient {

    val giteekey = MyAppConfig.giteekey;
    fun get(url: String, name: String): Response? {

        val client = OkHttpClient()

        // 构建请求URL
        val urlBuilder = HttpUrl.parse(url)!!.newBuilder()
        urlBuilder.addQueryParameter("p", name)
        urlBuilder.addQueryParameter("access_token", giteekey)
        val url1 = urlBuilder.build().toString()
        val request: Request = Request.Builder()
            .url(url1)
            .build()

            // 发送请求并获取响应
        return client.newCall(request).execute()

    }
}
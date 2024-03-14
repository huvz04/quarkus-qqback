package io.huvz.client

import io.huvz.config.MyAppConfig
import io.netty.handler.codec.http.HttpHeaders.addHeader
import okhttp3.*


class SyHttpClient {

    val xcxToken = MyAppConfig.xcxToken;
    fun get(url: String, name: String): Response? {
        val json ="{\n" +
                "    \"universityName\":\"$name\"\n" +
                "}"
        val client = OkHttpClient()
        val formBody: RequestBody = FormBody.create(
            MediaType.parse("application/json"),json
        )


        val request: Request = Request.Builder()
            .url(url)
            .post(formBody)

            //.addHeader("Content-Type","application/json")
            .addHeader("token",xcxToken)
            .build()

        // 发送请求并获取响应
        return client.newCall(request).execute()

    }
}
package io.huvz.client


import io.vertx.core.http.impl.HttpClientConnection.log
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response


class GitHttpClient {

    var  gitee: String = "114514";

    fun get(url:String, name:String): Response? {
        try {
            log.info("begin to get url====")
            val client = OkHttpClient().newBuilder().build()
            val access = gitee;
            val nurl = "$url?q=${name}&access_token=${access}"
            log.info("请求Gitee的URL:${nurl}")
            val request: Request = Request.Builder().url(nurl).get().build()
            return client.newCall(request).execute()
        } catch (e: Exception) {
            log.error("erro in${e.message}")
            return null;
        }
        return null;

    }
}
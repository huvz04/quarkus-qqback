package io.huvz.domain

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.quarkus.runtime.annotations.ConfigItem
import io.vertx.core.http.impl.HttpClientConnection.log
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty


class GitHttpClient {

    var  gitee: String = "114514";

    suspend fun get(url:String, name:String): HttpResponse? {
        try {
            log.info("begin to get url====")
            val client = HttpClient(CIO)
                val access = gitee;
                val nurl= "$url?q=${name}&access_token=${access}"
                log.info("请求Gitee的URL:${nurl}")
                val response: HttpResponse = client.get(nurl)
                return response
        }catch (e:Exception)
        {
            log.error("erro in${e.message}")
            return null;
        }
        return null;

    }
}
package io.huvz.Controller

import io.huvz.client.GitHttpClient
import io.huvz.client.GiteeApiClient
import io.huvz.domain.GiteeApi
import io.huvz.domain.vo.GiteeUsers
import io.quarkus.qute.Template
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.serialization.json.Json
import org.jboss.resteasy.reactive.RestQuery
import java.io.BufferedReader
import java.io.IOException


@Path("/v2api/view")
class InterfaceBResource {
    @Inject
    lateinit var gitee: Template
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/gitee")
    fun page(@RestQuery name:String): Any? {

            val client = GitHttpClient();
            val httpre = client.get(GiteeApi.SEARCH_USER.url,name) ?: return Response.status(500).entity("<h1>500</h1><br><h1>服务器内部错误</h1>").build()
//        if(httpre.s.value!=200) return Response.status(403).entity("远程服务器拒绝了操作").build();
            val body : String? = httpre.body()?.string()
            val json  = Json(){ignoreUnknownKeys=true}
            var list: Array<GiteeUsers>? = body.let { it?.let { it1 -> json.decodeFromString<Array<GiteeUsers>>(it1) } }
            if (list != null) {
                list = list.take(5).toTypedArray()
            }
            return gitee.data("users", list).render()
        }
}
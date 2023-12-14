package io.huvz

import freemarker.template.Template
import io.huvz.domain.*
import io.huvz.domain.vo.GiteeUsers
import io.ktor.client.call.*
import io.quarkiverse.freemarker.TemplatePath
import io.vertx.core.http.impl.HttpClientConnection.log
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.serialization.json.Json
import org.jboss.resteasy.reactive.RestQuery
import java.io.StringWriter
import java.util.*


@Path("/v2api/bot")
class AuthController {
    @Inject
    lateinit var browserService : BrowserFactory;

    @Inject
    @TemplatePath("giteeUser.ftl")
    lateinit var giteeUserHtml: Template

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello from RESTEasy Reactive"


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    fun getJson(): DeMessage{

        return DeMessage("测试")
    }

    @Path("/gitee")
    @POST
    @Produces("image/jpeg")
    fun gitee2img(@RestQuery name:String): Response? {
        val imageBytes: ByteArray = Base64.getDecoder().decode(browserService.getUrl4img(name))
        return Response.ok(imageBytes).build();
    }

    @Path("/searchGitee")
    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Produces("image/jpeg")
    suspend fun searchgitee(@RestQuery name:String): Response? {

        val client = GitHttpClient();
        val httpre = client.get(GiteeApi.SEARCH_USER.url,name) ?: return Response.status(500).entity("服务器内部错误").build()
        if(httpre.status.value!=200) return Response.status(403).entity("远程服务器拒绝了操作").build();
        val body :String?= httpre.call.body()
        val json  = Json(){ignoreUnknownKeys=true}
        var list: Array<GiteeUsers>? = body?.let { json.decodeFromString<Array<GiteeUsers>>(it) }
        if (list != null) {
            list = list.take(5).toTypedArray()
        }
        val stringWriter = StringWriter()
        val map = HashMap<String,Array<GiteeUsers>>()
        list?.let { map.put("users", it) };
        giteeUserHtml.process(map, stringWriter)
        val result = stringWriter.toString()

        val imageBytes: ByteArray = Base64.getDecoder().decode(browserService.htmlToImg(result))
        return Response.ok(imageBytes).build();
    }


}
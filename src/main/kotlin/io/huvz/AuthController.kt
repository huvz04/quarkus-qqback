package io.huvz


import io.huvz.client.GitHttpClient
import io.huvz.domain.*
import io.huvz.domain.vo.GiteeUsers
import io.quarkus.qute.Template
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.serialization.json.Json
import org.jboss.resteasy.reactive.RestQuery
import java.util.*


@Path("/v2api/bot")
class AuthController {
    @Inject
    lateinit var browserService : BrowserFactory;

    @Inject
    lateinit var gitee: Template

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
        //val imageBytes: ByteArray? = Base64.getDecoder().decode(browserService.getUrl4img(name)?:"1")
        return Response.ok(browserService.getUrl4img(name)).build();
    }

    @Path("/searchGitee")
    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Produces("image/jpeg")
    suspend fun searchgitee(@RestQuery name:String): Response? {

        return Response.ok( browserService.htmlToImg(name)).build();
    }


}
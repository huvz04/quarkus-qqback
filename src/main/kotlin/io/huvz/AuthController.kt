package io.huvz

import io.huvz.domain.BrowserFactory
import io.huvz.domain.DeMessage
import io.huvz.domain.GitHttpClient
import io.huvz.domain.GiteeApi
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.jboss.resteasy.reactive.RestQuery
import java.util.*

@Path("/v2api/bot")
class AuthController {
    @Inject
    lateinit var browserService : BrowserFactory;

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

    @Path("/searchgitee")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    fun searchgitee(@RestQuery name:String): Response? {
        val client = GitHttpClient();
        val body = client.get(GiteeApi.SEARCH_USER.url)
        val jsonresult = Json.decodeFromString<JsonObject>(body);
        return Response.ok(jsonresult).build();
    }

}
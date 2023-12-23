package io.huvz


import io.huvz.domain.BrowserFactory
import io.huvz.domain.DeMessage
import io.quarkus.qute.Template
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending

import io.vertx.core.VertxOptions
import io.vertx.mutiny.core.Vertx
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.jboss.resteasy.reactive.RestQuery


@Path("/v2api/bot")
class AuthController {
    @Inject
    lateinit var browserService : BrowserFactory;

    @Inject
    lateinit var gitee: Template


    @Inject
    lateinit var  vertx: Vertx

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


    @Path("/info")
    @POST
    @Produces("image/jpeg")
    suspend fun getInfo(@RestQuery url:String,@RestQuery name:String): Response? {
        try {
            when (url) {
                "gitee"-> return Uni.createFrom().item(Response.ok(browserService.getUrl4img(name)).build()).awaitSuspending();
                "nowcoder"-> return Uni.createFrom().item(Response.ok(browserService.nkToImg(name)).build()).awaitSuspending();
                "nc"-> return Uni.createFrom().item(Response.ok(browserService.nkToImg(name)).build()).awaitSuspending();
                "nk"-> return  Uni.createFrom().item(Response.ok(browserService.nkToImg(name)).build()).awaitSuspending();
            }
            return Uni.createFrom().item(Response.ok(browserService.getUrl4img(name)).build()).awaitSuspending();
        } catch (failure: Throwable) {
            return Response.status(500).entity("异步处理失败").build();
        }

    }
}
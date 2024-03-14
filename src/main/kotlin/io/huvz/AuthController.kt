package io.huvz


import io.huvz.client.LocalCache
import io.huvz.domain.DeMessage
import io.huvz.service.impl.*
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending

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
    lateinit var  jhcService: JhcService;
    @Inject
    lateinit var giteeSerivce : GiteeSerivce;
    @Inject
    lateinit var ncService : NcService;
    @Inject
    lateinit var githubSerivce: GithubSerivce;
    @Inject
    lateinit var syEDUSerivce: SyEDUSerivce

    @Inject
    lateinit var Lcache :LocalCache;

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
        return Response.ok(giteeSerivce.getUrl4img(name)).build();
    }

    @Path("/searchGitee")
    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Produces("image/jpeg")
    suspend fun searchgitee(@RestQuery name:String): Response? {

        return Response.ok( giteeSerivce.htmlToImg(name)).build();
    }


    @Path("/getUser")
    @POST
    @Produces("image/jpeg")
    suspend fun getUser(@RestQuery number:Int) : Response?{
        if(number>Lcache.cache.size) Response.status(500,"没有数据")
        val name = Lcache.cache.get(number-1).login

        return Response.ok(giteeSerivce.getUrl4img(name)).build();
    }


    @Path("/info")
    @POST
    @Produces("image/jpeg")
    suspend fun getInfo(@RestQuery url:String,@RestQuery name:String): Response? {
        try {
            when (url) {
                //gitee查询
                "gitee"-> return Uni.createFrom().item(Response.ok(giteeSerivce.getUrl4img(name)).build()).awaitSuspending();
                //gitee用户查询
                "gitee用户","giteeUser"-> return Uni.createFrom().item(Response.ok(giteeSerivce.htmlToImg(name)).build()).awaitSuspending();
                //牛客查询
                "nowcoder","nc","nk"-> return Uni.createFrom().item(Response.ok(ncService.nkToImg(name)).build()).awaitSuspending();

                //司越专升本查询
                "sy","zsb","专升本","学校"-> return Uni.createFrom().item(Response.ok(syEDUSerivce.htmlToImg(name)).build()).awaitSuspending();


                //jhc课表查询
                "jhc" -> return Uni.createFrom().item(Response.ok(jhcService.jhcToClass(name)).build()).awaitSuspending();
                "github" -> return Uni.createFrom().item(Response.ok(githubSerivce.getimg(name)).build()).awaitSuspending();

            }
            //默认用gitee
            return Uni.createFrom().item(Response.ok(giteeSerivce.getUrl4img(name)).build()).awaitSuspending();
        } catch (failure: Throwable) {
            return Response.status(500).entity("异步处理失败").build();
        }

    }
}
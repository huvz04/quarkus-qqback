package io.huvz.controller

import com.google.gson.Gson
import io.huvz.client.GitHttpClient
import io.huvz.client.LocalCache
import io.huvz.client.SyHttpClient
import io.huvz.domain.GiteeApi
import io.huvz.domain.vo.GiteeUsers
import io.huvz.domain.zsb.ResponseData
import io.huvz.domain.zsb.University
import io.quarkus.qute.Template
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.serialization.json.Json
import org.jboss.resteasy.reactive.RestQuery


@Path("/v2api/view")
class ResourceController {
    @Inject
    lateinit var gitee: Template
    @Inject
    lateinit var zsbSreach: Template

    @Inject
    lateinit var localCache : LocalCache;


    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/gitee")
    fun page(@RestQuery name:String): Any? {

            val client = GitHttpClient();
            val httpre = client.get(GiteeApi.SEARCH_USER.url,name) ?: return Response.status(500).entity("<h1>500</h1><br><h1>服务器内部错误</h1>").build()
//        if(httpre.s.value!=200) return Response.status(403).entity("远程服务器拒绝了操作").build();
            val body : String? = httpre.body()?.string()
            val json  = Json(){ignoreUnknownKeys=true}
            var list: MutableList <GiteeUsers>? = body.let { it?.let { it1 -> json.decodeFromString<MutableList <GiteeUsers>>(it1) } }

            if (list != null) {
                var num = 5;
                if(list.size<num) num=list.size
                list = list.subList(0,num)
            }
            if(list!=null) localCache.cache = list;
            return gitee.data("users", list).render()
        }


    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/sy")
    fun sypage(@RestQuery name:String): Any? {

        val client = SyHttpClient();
        val httpre = client.get(GiteeApi.SEARCH_SYZSB.url,name) ?: return Response.status(500).entity("<h1>500</h1><br><h1>服务器内部错误</h1>").build()
//        if(httpre.s.value!=200) return Response.status(403).entity("远程服务器拒绝了操作").build();
        if(httpre.body()==null) return zsbSreach.render()
        val body : String = httpre.body()!!.string()

            val json  = Json(){ignoreUnknownKeys = true;coerceInputValues = true}

//        try{
            val list: ResponseData = json.decodeFromString<ResponseData>(body)
            val data: List<University> = list.data.universities;
            val limitData= data.take(2);// 限制为前2个元素
            val names: List<String> = list.data.names;
            //zsbSreach.data("names", names)
            val dataMap = mutableMapOf<String, Any>()
            dataMap["names"] = names
            dataMap["universityVos"] = limitData
            return zsbSreach.data(dataMap).render()
//        }catch (e:Exception)
//        {
//            return Response.status(500).entity("<h1>500</h1><br><h1>JSON解析错误，都怪上游</h1>").build()
//        }

    }
}
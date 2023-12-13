package io.huvz.domain

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class GitHttpClient {


    fun get(url:String,name:String):String{
        val client = OkHttpClient()

        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        var params= HashMap<String, String>();
        params.put("p",name);

        val response: Response = client.newCall(request).execute()
        return response.body().toString()
    }
}
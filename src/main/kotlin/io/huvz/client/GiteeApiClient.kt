package io.huvz.client

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType

@Path("/gitee-api")
interface GiteeApiClient {
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    fun searchUsers(@QueryParam("q") query: String?): String?
}
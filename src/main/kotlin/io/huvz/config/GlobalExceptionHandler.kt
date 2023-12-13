package io.huvz.config

import io.vertx.core.http.impl.HttpClientConnection.log
import jakarta.ws.rs.core.Response
import org.jboss.resteasy.reactive.server.ServerExceptionMapper

class GlobalExceptionHandler {
    @ServerExceptionMapper
    fun globalExceptionHandler(e: Exception?): Response {
        log.error("global exception ", e)
        return Response.status(404).entity("not found").build()
    }
}
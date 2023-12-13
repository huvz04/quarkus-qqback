package io.huvz.config

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class DefaultExceptionHandler : ExceptionMapper<Exception?> {

    override fun toResponse(exception: Exception?): Response {
        return Response.status(500).entity("服务器内部错误:${exception?.message}").build()
    }
}
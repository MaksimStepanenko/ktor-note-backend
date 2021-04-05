package com.example.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*


data class HttpBinError(
    val request: String,
    val message: String,
    val code: HttpStatusCode,
    val cause: Throwable? = null
)

fun Application.configureErrors() {
    install(StatusPages) {
        exception<NumberFormatException> {
            val error = HttpBinError(
                code = HttpStatusCode.BadRequest,
                request = call.request.local.uri,
                message = "id param must be integer"
            )
            call.respond(error)
        }
        exception<NotFoundException> {
            val error = HttpBinError(
                code = HttpStatusCode.BadRequest,
                request = call.request.local.uri,
                message = "Note with such id not found"
            )
            call.respond(error)
        }
    }

    routing {
        route("{...}") {
            handle {
                val error = HttpBinError(
                    code = HttpStatusCode.NotFound,
                    request = call.request.local.uri,
                    message = "NOT FOUND"
                )
                call.respond(error)
            }
        }
    }

}
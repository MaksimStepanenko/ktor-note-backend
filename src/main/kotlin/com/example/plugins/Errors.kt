package com.example.plugins

import com.example.models.HttpBinError
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*


fun Application.configureErrors() {
    install(StatusPages) {
        exception<NumberFormatException> {
            val error = HttpBinError(
                code = HttpStatusCode.BadRequest.toString(),
                request = call.request.local.uri,
                message = "id param must be integer"
            )
            call.respond(HttpStatusCode.BadRequest, error)
        }
        exception<NotFoundException> {
            val error = HttpBinError(
                code = HttpStatusCode.NotFound.toString(),
                request = call.request.local.uri,
                message = "Note with such id not found"
            )
            call.respond(HttpStatusCode.NotFound, error)
        }
    }

    routing {
        route("{...}") {
            handle {
                val error = HttpBinError(
                    code = HttpStatusCode.NotFound.toString(),
                    request = call.request.local.uri,
                    message = "ROUTE NOT FOUND"
                )
                call.respond(HttpStatusCode.NotFound, error)
            }
        }
    }

}
package com.example

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

val lastId: Int = 1
val notes = mutableMapOf<Int, String>()

data class Note(val text: String)

data class HttpBinError(
    val request: String,
    val message: String,
    val code: HttpStatusCode,
    val cause: Throwable? = null
)

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        register(ContentType.Application.Xml, customXmlConverter())
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                indentObjectsWith(DefaultIndenter("  ", "\n"))
            })
        }
=    }

    install(StatusPages) {
        exception<NumberFormatException> {
            val error = HttpBinError(
                code = HttpStatusCode.BadRequest,
                request = call.request.local.uri,
                message = "id param must be integer"
            )
            call.respond(error)
        }
    }

    routing {

        route("/notes") {
            get {
                call.respond(notes)
            }
            post {
                val requestBody = call.receive<Note>()
                requestBody.text
            }
        }

        route("/notes/{id}") {

            get {
                val id = call.parameters["id"]?.toInt()

            }

            put {
                val id = call.parameters["id"]?.toInt()

            }

            delete {
                call.respond(status = HttpStatusCode.OK, "")
            }

        }

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


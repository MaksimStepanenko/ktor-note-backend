package com.example.plugins

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.jackson.*
import io.ktor.serialization.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
        gson {
        }
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}

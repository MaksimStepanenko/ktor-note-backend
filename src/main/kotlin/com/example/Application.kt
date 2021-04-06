package com.example

import com.example.plugins.configureErrors
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.application.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    configureRouting()
    configureSerialization()
    configureErrors()
}

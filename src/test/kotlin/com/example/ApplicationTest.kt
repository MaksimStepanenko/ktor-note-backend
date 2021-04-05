package com.example

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication {
            handleRequest(HttpMethod.Post, "/notes").apply {
                print(response.content)
//                assertEquals(HttpStatusCode.OK, response.status())
//                assertEquals("[]", response.content)
            }
            handleRequest(HttpMethod.Get, "/notes").apply {
                print(response.content)
//                assertEquals(HttpStatusCode.OK, response.status())
//                assertEquals("[]", response.content)
            }
        }
    }
}
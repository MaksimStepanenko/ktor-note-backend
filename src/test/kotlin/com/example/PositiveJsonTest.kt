package com.example

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PositiveJsonTest {
    private val postBody = "{\"value\": \"new note!\"}"

    @Test
    fun testGetList() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/notes").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("[]", response.content)
            }
        }
    }

    @Test
    fun testPostNote() {

        withTestApplication({ module(testing = true) }) {
            val request = handleRequest(HttpMethod.Post, "/notes") {
                addHeader(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
                addHeader(
                    HttpHeaders.Accept,
                    ContentType.Application.Json.toString()
                )
                setBody(postBody)
            }
            assertEquals(HttpStatusCode.OK, request.response.status())
            assertEquals("{\"id\":1,\"value\":\"new note!\"}", request.response.content)
        }
    }

    @Test
    fun testGetNote() {

        withTestApplication({ module(testing = true) }) {

            val postRequest = handleRequest(HttpMethod.Post, "/notes") {
                addHeader(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
                addHeader(
                    HttpHeaders.Accept,
                    ContentType.Application.Json.toString()
                )
                setBody(postBody)
            }

            assertEquals(HttpStatusCode.OK, postRequest.response.status())
            assertEquals("{\"id\":1,\"value\":\"new note!\"}", postRequest.response.content)

            handleRequest(HttpMethod.Get, "/notes/1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("{\"id\":1,\"value\":\"new note!\"}", response.content)
            }
        }
    }

    @Test
    fun testPutNote() {

        withTestApplication({ module(testing = true) }) {

            val postRequest = handleRequest(HttpMethod.Post, "/notes") {
                addHeader(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
                addHeader(
                    HttpHeaders.Accept,
                    ContentType.Application.Json.toString()
                )
                setBody(postBody)
            }

            assertEquals(HttpStatusCode.OK, postRequest.response.status())
            assertEquals("{\"id\":1,\"value\":\"new note!\"}", postRequest.response.content)

            val putBody = "{\"value\": \"new new note!\"}"
            val putRequest = handleRequest(HttpMethod.Put, "/notes/1") {
                addHeader(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
                addHeader(
                    HttpHeaders.Accept,
                    ContentType.Application.Json.toString()
                )
                setBody(putBody)
            }
            assertEquals(HttpStatusCode.OK, putRequest.response.status())
            assertEquals("{\"id\":1,\"value\":\"new new note!\"}", putRequest.response.content)
        }
    }
    @Test
    fun testDeleteNote() {

        withTestApplication({ module(testing = true) }) {

            val postRequest = handleRequest(HttpMethod.Post, "/notes") {
                addHeader(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
                addHeader(
                    HttpHeaders.Accept,
                    ContentType.Application.Json.toString()
                )
                setBody(postBody)
            }

            assertEquals(HttpStatusCode.OK, postRequest.response.status())
            assertEquals("{\"id\":1,\"value\":\"new note!\"}", postRequest.response.content)

            handleRequest(HttpMethod.Delete, "/notes/1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("", response.content)
            }
        }
    }
}
package com.example

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class NegativeJsonTest {
    private val postBody = "{\"value\": \"new note!\"}"
    

    @Test
    fun testIdIsNotANumber() {

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

            handleRequest(HttpMethod.Get, "/notes/test").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertEquals("id param must be integer", response.content)
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
    fun testNoteNotFound() {

        withTestApplication({ module(testing = true) }) {

            handleRequest(HttpMethod.Delete, "/notes/100").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
                assertEquals("Note with such id not found", response.content)
            }
        }
    }
}
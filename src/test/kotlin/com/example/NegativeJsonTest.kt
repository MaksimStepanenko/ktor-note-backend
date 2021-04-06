package com.example

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class NegativeJsonTest {


    @Test
    fun testIdIsNotANumber() {

        withTestApplication({ module(testing = true) }) {
            val postBody = "{\"value\": \"new note!\"}"

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
                assertEquals(
                    "{\"request\":\"/notes/test\",\"message\":\"id param must be integer\",\"code\":\"400 Bad Request\"}",
                    response.content
                )
            }
        }
    }

    @Test
    fun testPutNonexistent() {

        withTestApplication({ module(testing = true) }) {
            val putBody = "{\"value\": \"new new note!\"}"
            val putRequest = handleRequest(HttpMethod.Put, "/notes/100") {
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
            assertEquals(HttpStatusCode.NotFound, putRequest.response.status())
            assertEquals(
                "{\"request\":\"/notes/100\",\"message\":\"Note with such id not found\",\"code\":\"404 Not Found\"}",
                putRequest.response.content
            )
        }
    }

    @Test
    fun testDeleteNonexistent() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Delete, "/notes/100").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
                assertEquals(
                    "{\"request\":\"/notes/100\",\"message\":\"Note with such id not found\",\"code\":\"404 Not Found\"}",
                    response.content
                )
            }
        }
    }

    @Test
    fun testPathNotFound() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/nonexisten").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
                assertEquals(
                    "{\"request\":\"/nonexisten\",\"message\":\"ROUTE NOT FOUND\",\"code\":\"404 Not Found\"}",
                    response.content
                )
            }
        }
    }
}
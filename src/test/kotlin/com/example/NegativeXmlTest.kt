package com.example

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class NegativeXmlTest {
    @Test
    fun testIdIsNotANumber() {

        withTestApplication({ module(testing = true) }) {

            val getRequest = handleRequest(HttpMethod.Get, "/notes/test") {
                addHeader(HttpHeaders.Accept, ContentType.Text.Xml.toString())
            }
            assertEquals(HttpStatusCode.BadRequest, getRequest.response.status())
            assertEquals(
                "<HttpBinError><request>/notes/test</request><message>id param must be integer</message><code>400 Bad Request</code></HttpBinError>",
                getRequest.response.content
            )
        }
    }

    @Test
    fun testPutToNonexistent() {

        withTestApplication({ module(testing = true) }) {
            val putBody = "<item><value>new note!</value></item>"
            val putRequest = handleRequest(HttpMethod.Put, "/notes/100") {
                addHeader(
                    HttpHeaders.ContentType,
                    ContentType.Text.Xml.toString()
                )
                addHeader(
                    HttpHeaders.Accept,
                    ContentType.Text.Xml.toString()
                )
                setBody(putBody)
            }
            assertEquals(HttpStatusCode.NotFound, putRequest.response.status())
            assertEquals(
                "<HttpBinError><request>/notes/100</request><message>Note with such id not found</message><code>404 Not Found</code></HttpBinError>",
                putRequest.response.content
            )
        }
    }

    @Test
    fun testDeleteNonexistent() {
        withTestApplication({ module(testing = true) }) {
            val deleteRequest = handleRequest(HttpMethod.Delete, "/notes/100") {
                addHeader(HttpHeaders.Accept, ContentType.Text.Xml.toString())
            }
            assertEquals(HttpStatusCode.NotFound, deleteRequest.response.status())
            assertEquals(
                "<HttpBinError><request>/notes/100</request><message>Note with such id not found</message><code>404 Not Found</code></HttpBinError>",
                deleteRequest.response.content
            )
        }
    }

    @Test
    fun testPathNotFound() {
        withTestApplication({ module(testing = true) }) {
            val getRequest = handleRequest(HttpMethod.Get, "/nonexisten") {
                addHeader(HttpHeaders.Accept, ContentType.Text.Xml.toString())
            }
            assertEquals(HttpStatusCode.NotFound, getRequest.response.status())
            assertEquals(
                "<HttpBinError><request>/nonexisten</request><message>ROUTE NOT FOUND</message><code>404 Not Found</code></HttpBinError>",
                getRequest.response.content
            )
        }
    }
}
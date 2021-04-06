package com.example

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PositiveXmlTest {
    private val postBody = "<item><value>new note!</value></item>"

    @Test
    fun testGetList() {
        withTestApplication({ module(testing = true) }) {
            val getRequest = handleRequest(HttpMethod.Get, "/notes") {
                addHeader(
                    HttpHeaders.Accept,
                    ContentType.Text.Xml.toString()
                )
            }
            assertEquals(HttpStatusCode.OK, getRequest.response.status())
            assertEquals("<ArrayList/>", getRequest.response.content)
        }
    }

    @Test
    fun testPostNote() {

        withTestApplication({ module(testing = true) }) {
            val request = handleRequest(HttpMethod.Post, "/notes") {
                addHeader(
                    HttpHeaders.ContentType,
                    ContentType.Text.Xml.toString()
                )
                addHeader(
                    HttpHeaders.Accept,
                    ContentType.Text.Xml.toString()
                )
                setBody(postBody)
            }
            assertEquals(HttpStatusCode.OK, request.response.status())
            assertEquals("<item><id>1</id><value>new note!</value></item>", request.response.content)
        }
    }

    @Test
    fun testGetNote() {

        withTestApplication({ module(testing = true) }) {

            val postRequest = handleRequest(HttpMethod.Post, "/notes") {
                addHeader(
                    HttpHeaders.ContentType,
                    ContentType.Text.Xml.toString()
                )
                addHeader(
                    HttpHeaders.Accept,
                    ContentType.Text.Xml.toString()
                )
                setBody(postBody)
            }

            assertEquals(HttpStatusCode.OK, postRequest.response.status())
            assertEquals("<item><id>1</id><value>new note!</value></item>", postRequest.response.content)

            val getRequest = handleRequest(HttpMethod.Get, "/notes/1") {
                addHeader(
                    HttpHeaders.Accept,
                    ContentType.Text.Xml.toString()
                )
            }
            assertEquals(HttpStatusCode.OK, getRequest.response.status())
            assertEquals("<item><id>1</id><value>new note!</value></item>", getRequest.response.content)
        }
    }

    @Test
    fun testPutNote() {

        withTestApplication({ module(testing = true) }) {

            val postRequest = handleRequest(HttpMethod.Post, "/notes") {
                addHeader(
                    HttpHeaders.ContentType,
                    ContentType.Text.Xml.toString()
                )
                addHeader(
                    HttpHeaders.Accept,
                    ContentType.Text.Xml.toString()
                )
                setBody(postBody)
            }

            assertEquals(HttpStatusCode.OK, postRequest.response.status())
            assertEquals("<item><id>1</id><value>new note!</value></item>", postRequest.response.content)

            val putBody = "<item><id>1</id><value>new new note!</value></item>"
            val putRequest = handleRequest(HttpMethod.Put, "/notes/1") {
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
            assertEquals(HttpStatusCode.OK, putRequest.response.status())
            assertEquals("<item><id>1</id><value>new new note!</value></item>", putRequest.response.content)
        }
    }

    @Test
    fun testDeleteNote() {

        withTestApplication({ module(testing = true) }) {

            val postRequest = handleRequest(HttpMethod.Post, "/notes") {
                addHeader(HttpHeaders.ContentType, ContentType.Text.Xml.toString())
                addHeader(HttpHeaders.Accept, ContentType.Text.Xml.toString())
                setBody(postBody)
            }

            assertEquals(HttpStatusCode.OK, postRequest.response.status())
            assertEquals("<item><id>1</id><value>new note!</value></item>", postRequest.response.content)

            handleRequest(HttpMethod.Delete, "/notes/1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("", response.content)
            }
        }
    }
}
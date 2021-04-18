package com.joram

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationBTest {

    @Test
    fun testRoot() {
        withTestApplication({ baseModule(testing = true); moduleB(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    @Test
    fun testJsonGson() {
        withTestApplication({ baseModule(testing = true); moduleB(testing = true) }) {
            handleRequest(HttpMethod.Get, "/json/gson").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("{\"hello\":\"world\"}", response.content)
            }
        }
    }
}

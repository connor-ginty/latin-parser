package org.latinkotlinproject

import com.google.gson.JsonParser
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.latinkotlinproject.services.PerseusService

@DisplayName("Perseus Service Tests")
class PerseusServiceTest {

    private val perseusService = PerseusService(connectTimeoutMs = 5000, readTimeoutMs = 10000)

    @Nested
    @DisplayName("getPerseusResponseBody")
    inner class GetPerseusResponseBodyTests {

        @Test
        fun `returns Body object when hasBody is present`() {
            val json = """
                {
                  "RDF": {
                    "Annotation": {
                      "hasBody": {},
                      "Body": {"word": "via"}
                    }
                  }
                }
            """.trimIndent()
            val body = perseusService.getPerseusResponseBody(JsonParser.parseString(json).asJsonObject)

            assertTrue(body.isJsonObject)
            assertEquals("via", body.asJsonObject.get("word").asString)
        }

        @Test
        fun `returns Body array when hasBody is present and Body is an array`() {
            val json = """
                {
                  "RDF": {
                    "Annotation": {
                      "hasBody": {},
                      "Body": [{"word": "via"}, {"word": "viam"}]
                    }
                  }
                }
            """.trimIndent()
            val body = perseusService.getPerseusResponseBody(JsonParser.parseString(json).asJsonObject)

            assertTrue(body.isJsonArray)
            assertEquals(2, body.asJsonArray.size())
        }

        @Test
        fun `returns empty JsonArray when hasBody is absent`() {
            val json = """
                {
                  "RDF": {
                    "Annotation": {}
                  }
                }
            """.trimIndent()
            val body = perseusService.getPerseusResponseBody(JsonParser.parseString(json).asJsonObject)

            assertTrue(body.isJsonArray)
            assertEquals(0, body.asJsonArray.size())
        }
    }
}

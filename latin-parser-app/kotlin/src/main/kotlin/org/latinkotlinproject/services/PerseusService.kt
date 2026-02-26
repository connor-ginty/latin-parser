package org.latinkotlinproject.services

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.HttpURLConnection
import java.net.URL

@Service
class PerseusService(
    @Value("\${perseus.connect-timeout-ms:5000}") private val connectTimeoutMs: Int,
    @Value("\${perseus.read-timeout-ms:10000}") private val readTimeoutMs: Int
) {
    private val log = LoggerFactory.getLogger(PerseusService::class.java)

    fun makePerseusRequest(latinWord: String): JsonObject {
        val url = URL(
            "https://services.perseids.org/bsp/morphologyservice/analysis/word" +
                "?lang=lat&engine=morpheuslat&word=$latinWord"
        )
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = connectTimeoutMs
        connection.readTimeout = readTimeoutMs

        try {
            val responseString = connection.inputStream.bufferedReader().use { it.readText() }
            return JsonParser.parseString(responseString).asJsonObject
        } catch (e: Exception) {
            log.error("Perseus API request failed for word '{}': {}", latinWord, e.message, e)
            throw PerseusServiceException("Failed to fetch data from Perseus for word: $latinWord", e)
        } finally {
            connection.disconnect()
        }
    }

    fun getPerseusResponseBody(perseusServiceResponse: JsonObject): JsonElement {
        return try {
            val annotation = perseusServiceResponse
                .getAsJsonObject("RDF")
                ?.getAsJsonObject("Annotation")
                ?: return JsonArray()
            annotation.get("Body") ?: JsonArray()
        } catch (e: Exception) {
            log.warn("Unexpected Perseus response structure: {}", e.message)
            JsonArray()
        }
    }
}

class PerseusServiceException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

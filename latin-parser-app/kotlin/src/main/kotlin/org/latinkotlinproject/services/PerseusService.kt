package org.latinkotlinproject.services

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.springframework.stereotype.Service
import java.net.HttpURLConnection
import java.net.URL

@Service
class PerseusService {

  fun makePerseusRequest(latinWord: String): JsonObject {
    val url =
      URL("https://services.perseids.org/bsp/morphologyservice/analysis/word?lang=lat&engine=morpheuslat&word=$latinWord")
    val connection = url.openConnection() as HttpURLConnection

    connection.requestMethod = "GET"

    try {
      val responseString = connection.inputStream.bufferedReader().use { it.readText() }
      return JsonParser.parseString(responseString).asJsonObject
    } catch (e: Exception) {
      println("Error: ${e.message}")
    } finally {
      connection.disconnect()
    }
    return JsonParser.parseString(
      "{\"response\" : \"Request was not made or there was no data available.\"}"
    )
      .asJsonObject
  }

  private fun hasPerseusResponseBody(perseusServiceResponse: JsonObject): Boolean {
    val hasBody: JsonElement? = perseusServiceResponse
      .getAsJsonObject("RDF")
      .getAsJsonObject("Annotation")
      .get("hasBody")

    return hasBody !== null
  }

  fun getPerseusResponseBody(perseusServiceResponse: JsonObject): JsonElement {
    return if (hasPerseusResponseBody(perseusServiceResponse)) {
      perseusServiceResponse
        .getAsJsonObject("RDF")
        .getAsJsonObject("Annotation")
        .get("Body")
    } else {
      JsonArray()
    }
  }
}


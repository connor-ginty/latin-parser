package org.latinkotlinproject.services

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.net.HttpURLConnection
import java.net.URL
import org.jsoup.Jsoup
import org.springframework.stereotype.Service

@Service
class VocabService {

  private val latinCache = LatinCache()

  fun getVocabInformation(latinWord: String): JsonObject {
    val vocabInformationRequest: String = makeVocabRequest(latinWord)
    return latinCache.getFromCacheOrFetch(latinWord, { parseHtmlResponse(vocabInformationRequest) }, 300_000)
  }

  private fun makeVocabRequest(latinWord: String): String {
    val url =
      URL("https://www.latin-is-simple.com/api/vocabulary/search/?query=$latinWord&forms_only=true")
    val connection = url.openConnection() as HttpURLConnection

    connection.requestMethod = "GET"

    val responseCode = connection.responseCode
    return if (responseCode == HttpURLConnection.HTTP_OK) {
      connection.inputStream.bufferedReader().use { it.readText() }
    } else {
      connection.errorStream.bufferedReader().use { it.readText() }
    }
  }

  private fun parseHtmlResponse(html: String): JsonObject {
    val document = Jsoup.parse(html)
    val content: String = document
      .select("div.response-info>pre.prettyprint")
      .text()
      .trimIndent()
      .split("[ ")[1]

    val contentSubstring = content.substring(0, content.length - 1)
    val contentArray = "[$contentSubstring]"
    val contentObjectString = "{\"content\" : $contentArray}"
    println(contentObjectString)

    val contentObject: JsonObject = JsonParser.parseString(contentObjectString).asJsonObject

    return contentObject
  }

//  fun getType(latinWord: String): String{
//    val vocabData = getVocabInformation(latinWord)
//    val contentArray: JsonElement = vocabData["content"]
//    for (i in 0 until contentArray.asJsonArray.size()){
//
//    }
//  }
}
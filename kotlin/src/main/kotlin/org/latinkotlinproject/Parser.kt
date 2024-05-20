package org.latinkotlinproject

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.latinkotlinproject.data.ParsingData
import org.latinkotlinproject.services.LatinCache
import org.latinkotlinproject.services.PerseusService

private val perseusService = PerseusService()
private val latinCache = LatinCache()

class Parser {

  fun parseWord(latinWord: String): List<ParsingData> {
    val perseusResponse: JsonObject =
      latinCache.getFromCacheOrFetch(latinWord, { perseusService.makePerseusRequest(latinWord) }, 300_000)
    val wordInfoBody = perseusService.getPerseusResponseBody(perseusResponse)

    var parsedDataList: List<ParsingData> = mutableListOf<ParsingData>()

    // Body is an object = 1 part of speech per word
    if (wordInfoBody.isJsonObject) {
      parsedDataList = getDataFromBodyObject(wordInfoBody.asJsonObject)
    }

    // Body is an array = more than 1 part of speech per word
    if (wordInfoBody.isJsonArray) {
      parsedDataList = getDataFromBodyArray(wordInfoBody.asJsonArray)
    }
    return parsedDataList
  }

  private fun getDataFromBodyObject(body: JsonObject): MutableList<ParsingData> {
    val parsedDataList = mutableListOf<ParsingData>()
    val inflElement: JsonElement = body.asJsonObject
      .getAsJsonObject("rest")
      .getAsJsonObject("entry")
      .get("infl")
    if (inflElement.isJsonArray) {
      for (i in 0 until inflElement.asJsonArray.size()) {
        parsedDataList.add(
          ParsingData(
            inflElement.asJsonArray.get(i).asJsonObject.getAsJsonObject("pofs")?.asJsonObject?.get("\$")?.asString,
            inflElement.asJsonArray.get(i).asJsonObject.getAsJsonObject("decl")?.asJsonObject?.get("\$")?.asString,
            inflElement.asJsonArray.get(i).asJsonObject.getAsJsonObject("gend")?.asJsonObject?.get("\$")?.asString,
            inflElement.asJsonArray.get(i).asJsonObject.getAsJsonObject("case")?.asJsonObject?.get("\$")?.asString,
            inflElement.asJsonArray.get(i).asJsonObject.getAsJsonObject("num")?.asJsonObject?.get("\$")?.asString,
            inflElement.asJsonArray.get(i).asJsonObject.getAsJsonObject("stemtype")?.asJsonObject?.get("\$")?.asString,
            inflElement.asJsonArray.get(i).asJsonObject.getAsJsonObject("pers")?.asJsonObject?.get("\$")?.asString,
            inflElement.asJsonArray.get(i).asJsonObject.getAsJsonObject("tense")?.asJsonObject?.get("\$")?.asString,
            inflElement.asJsonArray.get(i).asJsonObject.getAsJsonObject("voice")?.asJsonObject?.get("\$")?.asString,
            inflElement.asJsonArray.get(i).asJsonObject.getAsJsonObject("mood")?.asJsonObject?.get("\$")?.asString,
          )
        )
      }
    }
    if (inflElement.isJsonObject) {
      return mutableListOf( // list with single JSON object
        ParsingData(
          inflElement.asJsonObject.getAsJsonObject("pofs")?.asJsonObject?.get("\$")?.asString,
          inflElement.asJsonObject.getAsJsonObject("decl")?.asJsonObject?.get("\$")?.asString,
          inflElement.asJsonObject.getAsJsonObject("gend")?.asJsonObject?.get("\$")?.asString,
          inflElement.asJsonObject.getAsJsonObject("case")?.asJsonObject?.get("\$")?.asString,
          inflElement.asJsonObject.getAsJsonObject("num")?.asJsonObject?.get("\$")?.asString,
          inflElement.asJsonObject.getAsJsonObject("stemtype")?.asJsonObject?.get("\$")?.asString,
          inflElement.asJsonObject.getAsJsonObject("pers")?.asJsonObject?.get("\$")?.asString,
          inflElement.asJsonObject.getAsJsonObject("tense")?.asJsonObject?.get("\$")?.asString,
          inflElement.asJsonObject.getAsJsonObject("voice")?.asJsonObject?.get("\$")?.asString,
          inflElement.asJsonObject.getAsJsonObject("mood")?.asJsonObject?.get("\$")?.asString,
        )
      )
    }
    return parsedDataList
  }

  private fun getDataFromBodyArray(body: JsonArray): MutableList<ParsingData> {
    val parsedDataList = mutableListOf<ParsingData>()
    val inflElementList = mutableListOf<JsonElement>()
    for (j in 0 until body.asJsonArray.size()) {
      inflElementList.add(body.asJsonArray
        .get(j).asJsonObject
        .getAsJsonObject("rest")
        .getAsJsonObject("entry")
        .get("infl")
      )
    }

    for (k in 0 until inflElementList.size) {
      if (inflElementList[k].isJsonArray) { // Infl is an array = more than 1 possible case, number, and gender for word
        for (x in 0 until inflElementList[k].asJsonArray.size()) {
          parsedDataList.add(
            ParsingData(
              inflElementList[k].asJsonArray.get(x).asJsonObject.getAsJsonObject("pofs")?.asJsonObject?.get("\$")?.asString,
              inflElementList[k].asJsonArray.get(x).asJsonObject.getAsJsonObject("decl")?.asJsonObject?.get("\$")?.asString,
              inflElementList[k].asJsonArray.get(x).asJsonObject.getAsJsonObject("gend")?.asJsonObject?.get("\$")?.asString,
              inflElementList[k].asJsonArray.get(x).asJsonObject.getAsJsonObject("case")?.asJsonObject?.get("\$")?.asString,
              inflElementList[k].asJsonArray.get(x).asJsonObject.getAsJsonObject("num")?.asJsonObject?.get("\$")?.asString,
              inflElementList[k].asJsonArray.get(x).asJsonObject.getAsJsonObject("stemtype")?.asJsonObject?.get("\$")?.asString,
              inflElementList[k].asJsonArray.get(x).asJsonObject.getAsJsonObject("pers")?.asJsonObject?.get("\$")?.asString,
              inflElementList[k].asJsonArray.get(x).asJsonObject.getAsJsonObject("tense")?.asJsonObject?.get("\$")?.asString,
              inflElementList[k].asJsonArray.get(x).asJsonObject.getAsJsonObject("voice")?.asJsonObject?.get("\$")?.asString,
              inflElementList[k].asJsonArray.get(x).asJsonObject.getAsJsonObject("mood")?.asJsonObject?.get("\$")?.asString,
            )
          )
        }
      }
      if (inflElementList[k].isJsonObject) { // Infl is an object = only 1 possible case, number, gender, tense, mood, or voice for word
        parsedDataList.add(
          ParsingData(
            inflElementList[k].asJsonObject.getAsJsonObject("pofs")?.get("\$")?.asString,
            inflElementList[k].asJsonObject.getAsJsonObject("decl")?.asJsonObject?.get("\$")?.asString,
            inflElementList[k].asJsonObject.getAsJsonObject("gend")?.asJsonObject?.get("\$")?.asString,
            inflElementList[k].asJsonObject.getAsJsonObject("case")?.asJsonObject?.get("\$")?.asString,
            inflElementList[k].asJsonObject.getAsJsonObject("num")?.asJsonObject?.get("\$")?.asString,
            inflElementList[k].asJsonObject.getAsJsonObject("stemtype")?.asJsonObject?.get("\$")?.asString,
            inflElementList[k].asJsonObject.getAsJsonObject("pers")?.asJsonObject?.get("\$")?.asString,
            inflElementList[k].asJsonObject.getAsJsonObject("tense")?.asJsonObject?.get("\$")?.asString,
            inflElementList[k].asJsonObject.getAsJsonObject("voice")?.asJsonObject?.get("\$")?.asString,
            inflElementList[k].asJsonObject.getAsJsonObject("mood")?.asJsonObject?.get("\$")?.asString,
          )
        )
      }
    }
    return parsedDataList
  }

  fun getPartOfSpeech(latinWord: String): List<String> {
    val perseusResponse: JsonObject =
      latinCache.getFromCacheOrFetch(latinWord, { perseusService.makePerseusRequest(latinWord) }, 30_000)
    val nounInfoBody = perseusService.getPerseusResponseBody(perseusResponse)
    val partOfSpeechList = mutableListOf<String>()

    if (nounInfoBody.isJsonArray) {
      val nounInfoBodyArray = nounInfoBody.asJsonArray
      for (i in 0 until nounInfoBodyArray.size()) {
        partOfSpeechList.add(
          nounInfoBodyArray
            .get(i).asJsonObject
            .getAsJsonObject("rest")
            .getAsJsonObject("entry")
            .getAsJsonObject("dict")
            .getAsJsonObject("pofs")
            .get("\$")
            .asString
        )
      }
    }
    if (nounInfoBody.isJsonObject) {
      partOfSpeechList.add(
        nounInfoBody.asJsonObject
          .getAsJsonObject("rest")
          .getAsJsonObject("entry")
          .getAsJsonObject("dict")
          .getAsJsonObject("pofs")
          .get("\$")
          .asString
      )
    }

    return partOfSpeechList
  }

//	fun getPartOfSpeech(data: JsonObject): String{
//		val wordInfoBody: JsonElement = perseusController.getPerseusResponseBody(data)
//		if (wordInfoBody.isJsonArray) {
//
//		}
//
//	}
}
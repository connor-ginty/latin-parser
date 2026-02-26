package org.latinkotlinproject.impl

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.latinkotlinproject.data.WordInflection
import org.latinkotlinproject.services.LatinCache
import org.latinkotlinproject.services.PerseusService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class Parser(
    private val perseusService: PerseusService,
    private val latinCache: LatinCache,
    @Value("\${cache.ttl-ms:300000}") private val cacheTtlMs: Long
) {
    private val log = LoggerFactory.getLogger(Parser::class.java)

    fun parseWord(latinWord: String): List<WordInflection> {
        val perseusResponse: JsonObject =
            latinCache.getFromCacheOrFetch(latinWord, { perseusService.makePerseusRequest(latinWord) }, cacheTtlMs)
        val wordInfoBody = perseusService.getPerseusResponseBody(perseusResponse)

        return when {
            wordInfoBody.isJsonArray && !wordInfoBody.asJsonArray.isEmpty -> getDataFromBodyArray(wordInfoBody.asJsonArray)
            wordInfoBody.isJsonObject -> getDataFromBodyObject(wordInfoBody.asJsonObject)
            else -> emptyList()
        }
    }

    private fun parseInflObject(infl: JsonObject): WordInflection = WordInflection(
        infl.getAsJsonObject("pofs")?.get("\$")?.asString,
        infl.getAsJsonObject("decl")?.get("\$")?.asString,
        infl.getAsJsonObject("gend")?.get("\$")?.asString,
        infl.getAsJsonObject("case")?.get("\$")?.asString,
        infl.getAsJsonObject("num")?.get("\$")?.asString,
        infl.getAsJsonObject("stemtype")?.get("\$")?.asString,
        infl.getAsJsonObject("pers")?.get("\$")?.asString,
        infl.getAsJsonObject("tense")?.get("\$")?.asString,
        infl.getAsJsonObject("voice")?.get("\$")?.asString,
        infl.getAsJsonObject("mood")?.get("\$")?.asString,
    )

    private fun inflElementToInflections(inflElement: JsonElement): List<WordInflection> = when {
        inflElement.isJsonArray -> inflElement.asJsonArray.map { parseInflObject(it.asJsonObject) }
        inflElement.isJsonObject -> listOf(parseInflObject(inflElement.asJsonObject))
        else -> emptyList()
    }

    private fun getDataFromBodyObject(body: JsonObject): List<WordInflection> {
        return try {
            val inflElement = body.getAsJsonObject("rest")
                ?.getAsJsonObject("entry")
                ?.get("infl")
                ?: return emptyList()
            inflElementToInflections(inflElement)
        } catch (e: Exception) {
            log.warn("Failed to parse Perseus body object: {}", e.message)
            emptyList()
        }
    }

    private fun getDataFromBodyArray(body: JsonArray): List<WordInflection> =
        body.flatMap { element ->
            try {
                val inflElement = element.asJsonObject
                    ?.getAsJsonObject("rest")
                    ?.getAsJsonObject("entry")
                    ?.get("infl")
                    ?: return@flatMap emptyList()
                inflElementToInflections(inflElement)
            } catch (e: Exception) {
                log.warn("Failed to parse Perseus body array element: {}", e.message)
                emptyList()
            }
        }
}

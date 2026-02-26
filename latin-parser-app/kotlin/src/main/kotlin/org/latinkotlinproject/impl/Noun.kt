package org.latinkotlinproject.impl

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.latinkotlinproject.data.NounData
import org.latinkotlinproject.services.LatinCache
import org.latinkotlinproject.services.PerseusService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class Noun(
    private val perseusService: PerseusService,
    private val latinCache: LatinCache,
    private val parser: Parser,
    @Value("\${cache.ttl-ms:300000}") private val cacheTtlMs: Long
) {

    fun declineNoun(latinNoun: String): Map<String, NounData> {
        val nounData = NounData()
        val declension: String = getDeclension(latinNoun)
        val gender: String = getGender(latinNoun)
        val stem: String? = getStem(latinNoun)
        val nominativeSingular: String = getNominative(latinNoun)

        val endings: Map<String, Map<String, String>>? = if (gender != "neuter") {
            when (declension) {
                "1st" -> nounData.firstDeclension
                "2nd" -> nounData.secondDeclension
                "3rd" -> nounData.thirdDeclension
                "4th" -> nounData.fourthDeclension
                "5th" -> nounData.fifthDeclension
                else -> null
            }
        } else {
            when (declension) {
                "2nd" -> nounData.secondDeclensionNeuter
                "3rd" -> nounData.thirdDeclensionNeuter
                "4th" -> nounData.fourthDeclensionNeuter
                else -> null
            }
        }

        val singularEndings = NounData(
            nominativeSingular,
            stem?.let { it + (endings?.get("Singular")?.get("gen") ?: "") },
            stem?.let { it + (endings?.get("Singular")?.get("dat") ?: "") },
            stem?.let { it + (endings?.get("Singular")?.get("acc") ?: "") },
            stem?.let { it + (endings?.get("Singular")?.get("abl") ?: "") }
        )

        val pluralEndings = NounData(
            stem?.let { it + (endings?.get("Plural")?.get("nom") ?: "") },
            stem?.let { it + (endings?.get("Plural")?.get("gen") ?: "") },
            stem?.let { it + (endings?.get("Plural")?.get("dat") ?: "") },
            stem?.let { it + (endings?.get("Plural")?.get("acc") ?: "") },
            stem?.let { it + (endings?.get("Plural")?.get("abl") ?: "") }
        )

        return mutableMapOf(
            "Singular" to singularEndings,
            "Plural" to pluralEndings
        )
    }

    private fun getBodyForWord(latinNoun: String): JsonElement {
        val perseusResponse = latinCache.getFromCacheOrFetch(
            latinNoun,
            { perseusService.makePerseusRequest(latinNoun) },
            cacheTtlMs
        )
        return perseusService.getPerseusResponseBody(perseusResponse)
    }

    private fun getEntry(nounInfoBody: JsonElement): JsonObject {
        val bodyObject = if (nounInfoBody.isJsonArray) nounInfoBody.asJsonArray.get(0).asJsonObject else nounInfoBody.asJsonObject
        return bodyObject.getAsJsonObject("rest").getAsJsonObject("entry")
    }

    fun getCase(latinNoun: String): String {
        val entry = getEntry(getBodyForWord(latinNoun))
        return entry.getAsJsonArray("infl").get(0).asJsonObject.getAsJsonObject("case").get("\$").asString
    }

    fun getNumber(latinNoun: String): String {
        val entry = getEntry(getBodyForWord(latinNoun))
        return entry.getAsJsonArray("infl").get(0).asJsonObject.getAsJsonObject("num").get("\$").asString
    }

    fun getStem(latinNoun: String): String {
        val inflections = parser.parseWord(latinNoun)
        val stemType = inflections[0].stemType
            ?: throw IllegalStateException("stemType is null for word: $latinNoun")
        return stemType.split("_")[1].substring(0, 2)
    }

    fun getGender(latinNoun: String): String {
        val entry = getEntry(getBodyForWord(latinNoun))
        return entry.getAsJsonObject("dict").getAsJsonObject("gend").get("\$").asString
    }

    fun getDeclension(latinNoun: String): String {
        val entry = getEntry(getBodyForWord(latinNoun))
        return entry.getAsJsonObject("dict").getAsJsonObject("decl").get("\$").asString
    }

    private fun getNominative(latinNoun: String): String {
        val nounInfoBody = getBodyForWord(latinNoun)
        val entry = getEntry(nounInfoBody)
        return if (nounInfoBody.isJsonArray) {
            entry.getAsJsonObject("dict").getAsJsonObject("decl").get("\$").asString
        } else {
            entry.getAsJsonObject("dict").getAsJsonObject("hdwd").get("\$").asString
        }
    }
}

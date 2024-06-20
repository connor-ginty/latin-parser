package org.latinkotlinproject.impl
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.latinkotlinproject.data.NounData
import org.latinkotlinproject.services.LatinCache
import org.latinkotlinproject.services.PerseusService
import java.lang.NullPointerException

private val perseusService = PerseusService()
private val latinCache = LatinCache()

class Noun {

	fun declineNoun(latinNoun: String): Map<String, NounData> {
		val noun = Noun()
		val nounData = NounData()
		val declension: String = noun.getDeclension(latinNoun)
		val gender: String = noun.getGender(latinNoun)
		val stem: String? = noun.getStem(latinNoun)
		val nominativeSingular: String = noun.getNominative(latinNoun)
		val endings: Map<String, Map<String, String>>?

		if (gender != "neuter") {
			endings = when (declension) {
				"1st" -> nounData.firstDeclension
				"2nd" -> nounData.secondDeclension
				"3rd" -> nounData.thirdDeclension
				"4th" -> nounData.fourthDeclension
				"5th" -> nounData.fifthDeclension
				else -> {
					null
				}
			}
		} else {
			endings = when (declension) {
				"2nd" -> nounData.secondDeclensionNeuter
				"3rd" -> nounData.thirdDeclensionNeuter
				"4th" -> nounData.fourthDeclensionNeuter
				else -> {
					null
				}
			}
		}

		val singularEndings = NounData(
			nominativeSingular,
			stem + endings?.get("Singular")?.get("gen"),
			stem + endings?.get("Singular")?.get("dat"),
			stem + endings?.get("Singular")?.get("acc"),
			stem + endings?.get("Singular")?.get("abl")
		)

		val pluralEndings = NounData(
			stem + endings?.get("Plural")?.get("nom"),
			stem + endings?.get("Plural")?.get("gen"),
			stem + endings?.get("Plural")?.get("dat"),
			stem + endings?.get("Plural")?.get("acc"),
			stem + endings?.get("Plural")?.get("abl")
		)

		return mutableMapOf(
			"Singular" to singularEndings,
			"Plural" to pluralEndings
		)
	}

	fun getCase(latinNoun: String): String {
		val perseusResponse: JsonObject =
			latinCache.getFromCacheOrFetch(latinNoun, { perseusService.makePerseusRequest(latinNoun) }, 30_000)
		val nounInfoBody = perseusService.getPerseusResponseBody(perseusResponse)

		val inflArray: JsonArray = if (nounInfoBody.isJsonArray) {
			nounInfoBody.asJsonArray
				.get(0).asJsonObject
				.getAsJsonObject("rest")
				.getAsJsonObject("entry")
				.getAsJsonArray("infl")
		} else {
			nounInfoBody.asJsonObject
				.getAsJsonObject("rest")
				.getAsJsonObject("entry")
				.getAsJsonArray("infl")
		}

		return inflArray
			.get(0).asJsonObject
			.getAsJsonObject("case")
			.get("\$").asString
	}

	fun getNumber(latinNoun: String): String {
		val perseusResponse: JsonObject =
			latinCache.getFromCacheOrFetch(latinNoun, { perseusService.makePerseusRequest(latinNoun) }, 30_000)
		val nounInfoBody = perseusService.getPerseusResponseBody(perseusResponse)

		val inflArray: JsonArray = if (nounInfoBody.isJsonArray) {
			nounInfoBody.asJsonArray
				.get(0).asJsonObject
				.getAsJsonObject("rest")
				.getAsJsonObject("entry")
				.getAsJsonArray("infl")
		} else {
			nounInfoBody.asJsonObject
				.getAsJsonObject("rest")
				.getAsJsonObject("entry")
				.getAsJsonArray("infl")
		}

		return inflArray
			.get(0).asJsonObject
			.getAsJsonObject("num")
			.get("\$").asString
	}

	fun getStem(latinNoun: String): String {
		val parser = Parser()
		val parsedData = parser.parseWord(latinNoun)
		val stemType = parsedData[0].stemType ?: throw NullPointerException()
		val endings = stemType.split("_")

		return endings[1].substring(0, 2)
	}

	fun getGender(latinNoun: String): String {
		val perseusResponse: JsonObject =
			latinCache.getFromCacheOrFetch(latinNoun, { perseusService.makePerseusRequest(latinNoun) }, 30_000)
		val nounInfoBody = perseusService.getPerseusResponseBody(perseusResponse)

		if (nounInfoBody.isJsonArray) {
			return nounInfoBody.asJsonArray
				.get(0).asJsonObject
				.getAsJsonObject("rest")
				.getAsJsonObject("entry")
				.getAsJsonObject("dict")
				.getAsJsonObject("gend")
				.get("\$")
				.asString
		}
		return nounInfoBody.asJsonObject
			.getAsJsonObject("rest")
			.getAsJsonObject("entry")
			.getAsJsonObject("dict")
			.getAsJsonObject("gend")
			.get("\$")
			.asString
	}

	fun getDeclension(latinNoun: String): String {
		val perseusResponse: JsonObject =
			latinCache.getFromCacheOrFetch(latinNoun, { perseusService.makePerseusRequest(latinNoun) }, 30_000)
		val nounInfoBody = perseusService.getPerseusResponseBody(perseusResponse)

		if (nounInfoBody.isJsonArray) {
			return nounInfoBody.asJsonArray
				.get(0).asJsonObject
				.getAsJsonObject("rest")
				.getAsJsonObject("entry")
				.getAsJsonObject("dict")
				.getAsJsonObject("decl")
				.get("\$")
				.asString
		}
		return nounInfoBody.asJsonObject
			.getAsJsonObject("rest")
			.getAsJsonObject("entry")
			.getAsJsonObject("dict")
			.getAsJsonObject("decl")
			.get("\$")
			.asString
	}

	private fun getNominative(latinNoun: String): String {
		val perseusResponse: JsonObject =
			latinCache.getFromCacheOrFetch(latinNoun, { perseusService.makePerseusRequest(latinNoun) }, 30_000)
		val nounInfoBody = perseusService.getPerseusResponseBody(perseusResponse)

		if (nounInfoBody.isJsonArray) {
			return nounInfoBody.asJsonArray
				.get(0).asJsonObject
				.getAsJsonObject("rest")
				.getAsJsonObject("entry")
				.getAsJsonObject("dict")
				.getAsJsonObject("decl")
				.get("\$")
				.asString
		}
		return nounInfoBody.asJsonObject
			.getAsJsonObject("rest")
			.getAsJsonObject("entry")
			.getAsJsonObject("dict")
			.getAsJsonObject("hdwd")
			.get("\$")
			.asString
	}
}
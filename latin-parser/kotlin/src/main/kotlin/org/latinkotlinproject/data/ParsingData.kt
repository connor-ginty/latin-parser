package org.latinkotlinproject.data

data class ParsingData(
    val partOfSpeech: String? = null,
    val declension: String? = null,
    val gender: String? = null,
    val case: String? = null,
    val number: String? = null,
    val stemType: String? = null,
    val person: String? = null,
    val tense: String? = null,
    val voice: String? = null,
    val mood: String? = null,
)
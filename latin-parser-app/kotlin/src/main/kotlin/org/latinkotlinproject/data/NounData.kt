package org.latinkotlinproject.data

data class NounData(

    // Noun Cases
    val nominative: String? = null,
    val genitive: String? = null,
    val dative: String? = null,
    val accusative: String? = null,
    val ablative: String? = null,

    // First Declension
    val firstDeclension: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "nom" to "a",
            "gen" to "ae",
            "dat" to "ae",
            "acc" to "am",
            "abl" to "a"
        ),
        "Plural" to mapOf(
            "nom" to "ae",
            "gen" to "arum",
            "dat" to "is",
            "acc" to "as",
            "abl" to "is"
        )
    ),

// ************************************************************

// Second Declension
    val secondDeclension: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "nom" to "us",
            "gen" to "i",
            "dat" to "o",
            "acc" to "um",
            "abl" to "o"
        ),
        "Plural" to mapOf(
            "nom" to "i",
            "gen" to "orum",
            "dat" to "is",
            "acc" to "os",
            "abl" to "is"
        )
    ),

    val secondDeclensionNeuter: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "nom" to "um",
            "gen" to "i",
            "dat" to "o",
            "acc" to "um",
            "abl" to "o"
        ),
        "Plural" to mapOf(
            "nom" to "a",
            "gen" to "orum",
            "dat" to "is",
            "acc" to "os",
            "abl" to "is"
        )
    ),

// ************************************************************

// Third Declension
    var thirdDeclension: Map<String, Map<String, String>> = mutableMapOf(
        "Singular" to mutableMapOf(
            "nom" to "",
            "gen" to "is",
            "dat" to "i",
            "acc" to "em",
            "abl" to "e"
        ),
        "Plural" to mapOf(
            "nom" to "es",
            "gen" to "um",
            "dat" to "ibus",
            "acc" to "es",
            "abl" to "ibus"
        ),
    ),

    var thirdDeclensionNeuter: Map<String, Map<String, String>> = mutableMapOf(
        "Singular" to mutableMapOf(
            "nom" to "",
            "gen" to "is",
            "dat" to "i",
            "acc" to "",
            "abl" to "e"
        ),
        "Plural" to mapOf(
            "nom" to "a",
            "gen" to "um",
            "dat" to "ibus",
            "acc" to "a",
            "abl" to "ibus"
        ),
    ),

// ************************************************************

// Fourth Declension
    val fourthDeclension: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "nom" to "us",
            "gen" to "us",
            "dat" to "ui",
            "acc" to "um",
            "abl" to "u"
        ),
        "Plural" to mapOf(
            "nom" to "us",
            "gen" to "uum",
            "dat" to "ibus",
            "acc" to "us",
            "abl" to "ibus"
        )
    ),

    val fourthDeclensionNeuter: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "nom" to "u",
            "gen" to "us",
            "dat" to "u",
            "acc" to "u",
            "abl" to "u"
        ),
        "Plural" to mapOf(
            "nom" to "ua",
            "gen" to "uum",
            "dat" to "ibus",
            "acc" to "ua",
            "abl" to "ibus"
        )
    ),

// ************************************************************

// Fifth Declension
    val fifthDeclension: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "nom" to "es",
            "gen" to "ei",
            "dat" to "ei",
            "acc" to "em",
            "abl" to "e"
        ),
        "Plural" to mapOf(
            "nom" to "es",
            "gen" to "erum",
            "dat" to "ebus",
            "acc" to "es",
            "abl" to "ebus"
        )
    )
)
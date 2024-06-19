package org.latinkotlinproject.data

data class VerbData(
    val firstPerson: String? = null,
    val secondPerson: String? = null,
    val thirdPerson: String? = null,

    // Present Tense
    val presentTenseActive: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "1st" to "o",
            "2nd" to "s",
            "3rd" to "t",
        ),
        "Plural" to mapOf(
            "1st" to "mus",
            "2nd" to "tis",
            "3rd" to "nt",
        )
    ),

    val presentTensePassive: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "1st" to "r",
            "2nd" to "ris",
            "3rd" to "tur",
        ),
        "Plural" to mapOf(
            "1st" to "mur",
            "2nd" to "mini",
            "3rd" to "ntur",
        )
    ),

// ************************************************************

// Imperfect Tense
    val imperfectTenseActive: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "1st" to "bam",
            "2nd" to "bas",
            "3rd" to "bat",
        ),
        "Plural" to mapOf(
            "1st" to "bamus",
            "2nd" to "batis",
            "3rd" to "bant",
        )
    ),

    val imperfectTensePassive: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "1st" to "bar",
            "2nd" to "baris",
            "3rd" to "batur",
        ),
        "Plural" to mapOf(
            "1st" to "bamur",
            "2nd" to "bamini",
            "3rd" to "bantur",
        )
    ),

// ************************************************************

// Future Tense
// First and Second Conjugations
    val futureTenseActiveFirstAndSecond: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "1st" to "bo",
            "2nd" to "bis",
            "3rd" to "bit",
        ),
        "Plural" to mapOf(
            "1st" to "bimus",
            "2nd" to "bitis",
            "3rd" to "bunt",
        )
    ),

    val futureTensePassiveFirstAndSecond: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "1st" to "bor",
            "2nd" to "beris",
            "3rd" to "bitur",
        ),
        "Plural" to mapOf(
            "1st" to "bimur",
            "2nd" to "bimini",
            "3rd" to "buntur",
        )
    ),

// Third and Fourth Conjugations
    val futureTenseActiveSingularThirdAndFourth: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "1st" to "am",
            "2nd" to "es",
            "3rd" to "et",
        ),
        "Plural" to mapOf(
            "1st" to "emus",
            "2nd" to "etis",
            "3rd" to "ent",
        )
    ),

    val futureTensePassiveThirdAndFourth: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "1st" to "ar",
            "2nd" to "eris",
            "3rd" to "etur",
        ),
        "Plural" to mapOf(
            "1st" to "emur",
            "2nd" to "emini",
            "3rd" to "entur",
        )
    ),

// ************************************************************

// Perfect Tense
    val perfectTenseActive: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "1st" to "i",
            "2nd" to "isti",
            "3rd" to "it",
        ),
        "Plural" to mapOf(
            "1st" to "imus",
            "2nd" to "istis",
            "3rd" to "erunt",
        )
    ),

// ************************************************************

// Pluperfect Tense
    val pluperfectTenseActive: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "1st" to "eram",
            "2nd" to "eras",
            "3rd" to "erat",
        ),
        "Plural" to mapOf(
            "1st" to "eramus",
            "2nd" to "eratis",
            "3rd" to "erant",
        )
    ),

// ************************************************************

// Future Perfect Tense
    val futurePerfectTenseActive: Map<String, Map<String, String>> = mapOf(
        "Singular" to mapOf(
            "1st" to "ero",
            "2nd" to "eris",
            "3rd" to "erit",
        ),
        "Plural" to mapOf(
            "1st" to "erimus",
            "2nd" to "eritis",
            "3rd" to "erint",
        )
    )
)
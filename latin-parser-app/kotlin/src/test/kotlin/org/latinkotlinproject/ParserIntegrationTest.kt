package org.latinkotlinproject

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.latinkotlinproject.data.WordInflection
import org.latinkotlinproject.impl.Parser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("Parser Tests")
class ParserTest {

    @Autowired
    private lateinit var parser: Parser

    @Nested
    @DisplayName("Parsing Data Types Tests")
    inner class WordInflectionTypesTests {

        @Test
        fun testBodyIsArrayAndInflIsArray_Noun() {
            val expected = listOf(
                WordInflection(
                    "noun",
                    "1st",
                    "feminine",
                    "ablative",
                    "singular",
                    "a_ae",
                    null,
                    null,
                    null,
                    null
                ),
                WordInflection(
                    "noun",
                    "1st",
                    "feminine",
                    "nominative",
                    "singular",
                    "a_ae",
                    null,
                    null,
                    null,
                    null
                ),
                WordInflection(
                    "noun",
                    "1st",
                    "feminine",
                    "vocative",
                    "singular",
                    "a_ae",
                    null,
                    null,
                    null,
                    null
                ),
                WordInflection(
                    "verb",
                    null,
                    null,
                    null,
                    "singular",
                    "conj1",
                    "2nd",
                    "present",
                    "active",
                    "imperative"
                )
            )
            val result: List<WordInflection> = parser.parseWord("via")

            assertEquals(expected, result)
        }

        @Test
        fun testBodyIsObjectAndInflIsArray_Verb() {
            val expected = listOf(
                WordInflection(
                    "verb",
                    null,
                    null,
                    null,
                    "singular",
                    "conj3_io",
                    "2nd",
                    "present",
                    "passive",
                    "imperative"
                ),
                WordInflection(
                    "verb",
                    null,
                    null,
                    null,
                    "singular",
                    "conj3_io",
                    "2nd",
                    "present",
                    "passive",
                    "indicative"
                ),
                WordInflection(
                    "verb",
                    null,
                    null,
                    null,
                    null,
                    "conj3_io",
                    null,
                    "present",
                    "active",
                    "infinitive"
                )
            )
            val result: List<WordInflection> = parser.parseWord("facere")

            assertEquals(expected, result)
        }

        @Test
        fun testBodyIsObjectAndInflIsObject() {
            val expected = listOf(
                WordInflection(
                    "noun",
                    "1st",
                    "feminine",
                    "accusative",
                    "singular",
                    "a_ae",
                    null,
                    null,
                    null,
                    null
                )
            )
            val result: List<WordInflection> = parser.parseWord("turbam")
            assertEquals(expected, result)
        }

        @Test
        fun testBodyIsArrayAndInflIsObject() {
            val expected = listOf(
                WordInflection(
                    "noun",
                    "1st",
                    "feminine",
                    "accusative",
                    "plural",
                    "a_ae",
                    null,
                    null,
                    null,
                    null
                ),
                WordInflection(
                    "verb",
                    null,
                    null,
                    null,
                    "singular",
                    "conj1",
                    "2nd",
                    "present",
                    "active",
                    "indicative"
                ),
            )
            val result: List<WordInflection> = parser.parseWord("fugas")
            assertEquals(expected, result)
        }
    }
}

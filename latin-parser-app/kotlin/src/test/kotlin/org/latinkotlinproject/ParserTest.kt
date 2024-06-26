package org.latinkotlinproject
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.latinkotlinproject.data.NounData
import org.latinkotlinproject.data.ParsingData
import org.latinkotlinproject.impl.Noun
import org.latinkotlinproject.impl.Parser

@DisplayName("Parser Tests")
class ParserTest {
  private val noun = Noun()
  private val parser = Parser()

  @Nested
  @DisplayName("Parsing Data Types Tests")
  inner class ParsingDataTypesTests {

    @Test
    fun testBodyIsArrayAndInflIsArray_Noun() {
      val expected = listOf(
        ParsingData(
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
        ParsingData(
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
        ParsingData(
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
        ParsingData(
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
      val result: List<ParsingData> = parser.parseWord("via")

      assertEquals(expected, result)
    }

    @Test
    fun testBodyIsObjectAndInflIsArray_Verb() {
      val expected = listOf(
        ParsingData(
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
        ParsingData(
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
        ParsingData(
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
      val result: List<ParsingData> = parser.parseWord("facere")

      assertEquals(expected, result)
    }

    @Test
    fun testBodyIsObjectAndInflIsObject() {
      val expected = listOf(
        ParsingData(
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
      val result: List<ParsingData> = parser.parseWord("turbam")
      assertEquals(expected, result)
    }

    @Test
    fun testBodyIsArrayAndInflIsObject() {
      val expected = listOf(
        ParsingData(
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
        ParsingData(
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
      val result: List<ParsingData> = parser.parseWord("fugas")
      assertEquals(expected, result)
    }
  }
}

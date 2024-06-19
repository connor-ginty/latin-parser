package org.latinkotlinproject
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.latinkotlinproject.data.NounData
import org.latinkotlinproject.data.ParsingData

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

//      assertEquals(expected.size, result.size)
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
      print(result)
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

    @Nested
    @DisplayName("Edge Case Parser Tests")
    inner class EdgeCaseParserTests {

      @Test
      @Disabled("More work needed on implementation")
      fun testIndeclinableWord() {
//        val expected: String = "exclamation"
//        val result: String = parser.getPartOfSpeech("eheu")
//        assertEquals(expected, result)
      }

      @Test
      @Disabled("More work needed on implementation")
      fun testDefectiveNoun() {
        val expected = "1st"
        val result: String = noun.getDeclension("vim")
        assertEquals(expected, result)
      }
    }

    @Nested
    @DisplayName("Declining Nouns Tests")
    inner class DecliningNounsTests {

      @Test
      fun declineSimpleNounFirstDeclension() {
        val result: Map<String, NounData> = noun.declineNoun("puella")
        println(result)
      }

      @Test
      fun declineSimpleNounSecondDeclension() {
        val result: Map<String, NounData> = noun.declineNoun("puer")
        println(result)
      }

      @Test
      @Disabled("More work needed on implementation")
      fun declineStemChangingNoun() {
        val result: Map<String, NounData> = noun.declineNoun("homo")
        println(result)
      }

      @Test
      fun testGettingStemForStemChangingNoun() {
        val result: String? = noun.getStem("hominem")
        println(result)
      }
    }

    @Nested
    @DisplayName("Negative Path")
    inner class NegativePathParserTests {

      @Test
      @Disabled("More work needed on implementation")
      fun testNounFirstDeclensionIsIncorrect() {
//					val expected = "1st"
////					val result: String = noun.getDeclension("locus")
////					assertNotEquals(expected, result)
        println("Negative path tests will go here.")
      }
    }
  }
}

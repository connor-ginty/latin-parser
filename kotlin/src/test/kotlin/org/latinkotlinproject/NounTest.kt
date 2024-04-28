package org.latinkotlinproject

import org.junit.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@DisplayName("Noun Tests")
class NounTest {
	private val noun = Noun()
	private val parser = Parser()

	@Nested
	@DisplayName("Noun Gender Tests")
	inner class NounGenderTests {

		@Test
		fun testFeminineNounGenderIsCorrect() {
			val expected = "feminine"
			val result: String = noun.getGender("puella")
			assertEquals(expected, result)
		}

		@Test
		fun testMasculineNounGenderIsCorrect() {
			val expected = "masculine"
			val result: String = noun.getGender("imperator")
			assertEquals(expected, result)
		}

		@Test
		fun testNeuterNounGenderIsCorrect() {
			val expected = "neuter"
			val result: String = noun.getGender("periculum")
			assertEquals(expected, result)
		}
	}

	@Nested
	@DisplayName("Noun Stem Tests")
	inner class NounStemTests {

		@Test
		fun testNounStemIsCorrect() {
			val expected = "ae"
			val result: String? = noun.getStem("puella")
			assertEquals(expected, result)
		}
	}

	@Test
	fun testNounStemChangerIsCorrect() {
		val expected = "or"
		val result: String? = noun.getStem("corpus")
		assertEquals(expected, result)
	}

	@Nested
	@DisplayName("Noun Declension Tests")
	inner class NounDeclensionTests {

		@Test
		fun testNounFirstDeclensionIsCorrect() {
			val expected = "1st"
			val result: String = noun.getDeclension("coma")
			assertEquals(expected, result)
		}

		@Test
		fun testNounSecondDeclensionIsCorrect() {
			val expected = "2nd"
			val result: String = noun.getDeclension("somnus")
			assertEquals(expected, result)
		}

		@Test
		fun testNounThirdDeclensionIsCorrect() {
			val expected = "3rd"
			val result: String = noun.getDeclension("arbor")
			assertEquals(expected, result)
		}

		@Test
		fun testNounFourthDeclensionIsCorrect() {
			val expected = "4th"
			val result: String = noun.getDeclension("manus")
			assertEquals(expected, result)
		}

		@Test
		fun testNounCanAlsoBeVerbPartOfSpeech() {
			val expected = "1st"
			val result: String = noun.getDeclension("via")
			assertEquals(expected, result)
		}

		@Test
		fun testNounFifthDeclensionIsCorrect() {
			val expected = "5th"
			val result: String = noun.getDeclension("spes")
			assertEquals(expected, result)
		}

		@Test
		fun testNounFirstDeclensionIsIncorrect() {
			val expected = "1st"
			val result: String = noun.getDeclension("locus")
			assertNotEquals(expected, result)
		}
	}

	@Nested
	@DisplayName("Noun Part Of Speech Tests")
	inner class NounPartOfSpeechTests {

		@Test
		fun testPartOfSpeechIsNoun() {
			val expected = listOf("noun")
			val result: List<String> = parser.getPartOfSpeech("locus")
			assertEquals(expected, result)
		}

		@Test
		fun testNounOrParticiplePartOfSpeechIsCorrect() {
			val expected = listOf("verb", "noun")
			val result: List<String> = parser.getPartOfSpeech("adventum")
			assertEquals(expected, result)
		}
	}
}
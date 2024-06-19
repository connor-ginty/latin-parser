package org.latinkotlinproject

import org.junit.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@DisplayName("Verb Tests")
class VerbTest {
	private val noun = Noun()

	@Nested
	@DisplayName("Happy Path")
	inner class HappyPathNounTests {

		@Test
		fun testFeminineNounGenderIsCorrect() {
			val expected = "feminine"
			val result: String = noun.getGender("puella")
			assertEquals(expected, result)
		}

		@Nested
		@DisplayName("Edge Cases")
		inner class EdgeCaseVerbTests {

			@Test
			fun testNounFirstDeclensionIsCorrect() {
				val expected = "1st"
				val result: String = noun.getDeclension("via")
				assertEquals(expected, result)
			}

			@Nested
			@DisplayName("Negative Path")
			inner class NegativePathVerbTests {

				@Test
				fun testNounFirstDeclensionIsInorrect() {
					val expected = "1st"
					val result: String = noun.getDeclension("locus")
					assertNotEquals(expected, result)
				}
			}
		}
	}
}
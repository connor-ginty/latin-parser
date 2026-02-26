package org.latinkotlinproject

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.latinkotlinproject.services.LatinCache

@DisplayName("Cache Tests")
class CacheValidationTests {

    private val mockApiRequest: (String) -> JsonObject = mockk()
    private lateinit var latinCache: LatinCache

    private val testWord = "examen"
    private val cachedTestValue: JsonObject = JsonParser
        .parseString("{\"value\" : \"$testWord\"}").asJsonObject

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        latinCache = LatinCache()
    }

    @Test
    fun `cached value is returned without re-fetching`() {
        every { mockApiRequest(testWord) } returns cachedTestValue

        val result1 = latinCache.getFromCacheOrFetch(testWord, { mockApiRequest(testWord) }, 5_000)
        val result2 = latinCache.getFromCacheOrFetch(testWord, { mockApiRequest(testWord) }, 5_000)

        verify(exactly = 1) { mockApiRequest(testWord) }
        assertEquals(cachedTestValue, result1)
        assertEquals(cachedTestValue, result2)
    }

    @Test
    fun `expired cache entry is re-fetched`() {
        every { mockApiRequest(testWord) } returns cachedTestValue

        // TTL of 0ms means every read is considered expired
        latinCache.getFromCacheOrFetch(testWord, { mockApiRequest(testWord) }, 0)
        latinCache.getFromCacheOrFetch(testWord, { mockApiRequest(testWord) }, 0)

        verify(exactly = 2) { mockApiRequest(testWord) }
    }

    @Test
    fun `each key is cached independently`() {
        val wordA = "via"
        val wordB = "turbam"
        val valueA = JsonParser.parseString("{\"word\": \"via\"}").asJsonObject
        val valueB = JsonParser.parseString("{\"word\": \"turbam\"}").asJsonObject

        every { mockApiRequest(wordA) } returns valueA
        every { mockApiRequest(wordB) } returns valueB

        latinCache.getFromCacheOrFetch(wordA, { mockApiRequest(wordA) }, 5_000)
        latinCache.getFromCacheOrFetch(wordB, { mockApiRequest(wordB) }, 5_000)
        // Second fetches should hit cache for each key independently
        latinCache.getFromCacheOrFetch(wordA, { mockApiRequest(wordA) }, 5_000)
        latinCache.getFromCacheOrFetch(wordB, { mockApiRequest(wordB) }, 5_000)

        verify(exactly = 1) { mockApiRequest(wordA) }
        verify(exactly = 1) { mockApiRequest(wordB) }
    }

    @Test
    fun `clearCache empties the cache`() {
        every { mockApiRequest(testWord) } returns cachedTestValue

        latinCache.getFromCacheOrFetch(testWord, { mockApiRequest(testWord) }, 5_000)
        assertFalse(latinCache.isEmpty())

        latinCache.clearCache()
        assertTrue(latinCache.isEmpty())
    }

    @Test
    fun `isEmpty returns true on a fresh cache`() {
        assertTrue(latinCache.isEmpty())
    }
}

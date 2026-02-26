package org.latinkotlinproject.services

import com.google.gson.JsonObject
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class LatinCache {
    private val log = LoggerFactory.getLogger(LatinCache::class.java)
    private val cache: MutableMap<String, CachedData> = ConcurrentHashMap()

    fun getFromCacheOrFetch(
        key: String,
        apiRequest: () -> JsonObject,
        cacheValidityInMillis: Long
    ): JsonObject {
        val cachedData = cache[key]

        if (cachedData != null && System.currentTimeMillis() - cachedData.timestamp < cacheValidityInMillis) {
            log.debug("Cache hit for key: {}", key)
            return cachedData.data
        }

        log.debug("Cache miss for key: {}, fetching from API", key)
        val fetchedData = apiRequest()
        cache[key] = CachedData(fetchedData, System.currentTimeMillis())
        return fetchedData
    }

    private data class CachedData(val data: JsonObject, val timestamp: Long)

    fun clearCache() {
        cache.clear()
    }

    fun isEmpty(): Boolean {
        return cache.isEmpty()
    }
}

package org.latinkotlinproject.services

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class LatinCache {
  private val cache: MutableMap<String, Any> = ConcurrentHashMap()
  private val perseusService = PerseusService()

  fun getFromCacheOrFetch(
    key: String,
    apiRequest: (String) -> JsonElement = {
      perseusService.makePerseusRequest(key) }, // sends request to Perseus by default
    cacheValidityInMillis: Long
  ): JsonObject {
    val cachedData = cache[key] as? CachedData

    if (cachedData != null && System.currentTimeMillis() - cachedData.timestamp < cacheValidityInMillis) {
      return cachedData.data
    }
    val fetchedData = apiRequest(key).asJsonObject
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
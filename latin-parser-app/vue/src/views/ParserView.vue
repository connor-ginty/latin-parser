<template>
  <div class="parser">
    <ParserInput @search="handleSearch" />
    <ParserOutput :parsedData="parsedData" :errorMessage="errorMessage" :resultsMessage="resultsMessage" />
    <SearchHistory :searchHistory="searchHistory" @parse-word="handleSearch" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import { apiService } from '@/service/apiServices'
import type { ParsedData } from '@/types'
import ParserInput from '@/components/ParserInput.vue'
import ParserOutput from '@/components/ParserOutput.vue'
import SearchHistory from '@/components/SearchHistory.vue'

const parsedData = ref<ParsedData[]>([])
const searchHistory = ref<string[]>([])
const resultsMessage = ref('')
const errorMessage = ref('')

async function handleSearch(word: string): Promise<void> {
  errorMessage.value = ''
  try {
    const response = await apiService.parseWord(word)
    parsedData.value = response.data.parsedDataList
    resultsMessage.value = response.data.message
    if (!searchHistory.value.includes(word)) {
      searchHistory.value.unshift(word)
    }
  } catch (error) {
    console.error('Error fetching data', error)
    if (axios.isAxiosError(error)) {
      errorMessage.value = error.response?.data?.message ?? ''
    }
  }
}
</script>

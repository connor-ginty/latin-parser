<template>
  <div class="parser-input">
    <input type="text" v-model="searchQuery" placeholder="Enter Latin word here"
      @keydown.enter="handleSubmissionClick(this.searchQuery)">
    <button type="submit" @click="handleSubmissionClick(this.searchQuery)">Parse Word</button>
    <ParserOutput :parsedData="parsedData" :errorMessage="errorMessage" :resultsMessage="resultsMessage"/>
    <SearchHistory :searchHistory="searchHistory" :errorMessage="errorMessage"
      :handleSubmissionClick="handleSubmissionClick" />
  </div>
</template>

<script>
import { apiService } from '@/service/apiServices'
import ParserOutput from '@/components/ParserOutput.vue'
import SearchHistory from '@/components/SearchHistory.vue'

export default {
  name: 'ParserInput',
  components: {
    SearchHistory,
    ParserOutput
  },
  data() {
    return {
      searchQuery: '',
      parsedData: [],
      searchHistory: [],
      resultsMessage: '',
      errorMessage: ''
    }
  },
  methods: {
    async handleSubmissionClick(word) {
      this.resetErrorMessage()
      if (word) {
        try {
          const response = await this.fetchParsingData(word)
          console.log(response)
          this.parsedData = response.data.parsedDataList
          this.resultsMessage = response.data.message
          this.searchQuery = ''
          this.addToSearchHistory(word)
        } catch (error) {
          console.error('Error fetching data', error)
          this.updateErrorMessage(error.response.data.message)
          console.log(this.errorMessage)
        }
      }
    },
    async fetchParsingData(word) {
      return await apiService.parseWord(word)
    },
    updateErrorMessage(newMessage) {
      this.errorMessage = newMessage
    },
    resetErrorMessage() {
      this.errorMessage = ''
    },
    addToSearchHistory(word) {
      console.log(this.errorMessage)
      if (!this.searchHistory.includes(word)) {
        if (this.errorMessage === '') {
          this.searchHistory.unshift(word)
        }
      }
    }
  }
}
</script>
<template>
  <div class="parser-input">
    <input type="text" v-model="searchQuery" placeholder="Enter Latin word here"
      @keydown.enter="handleSubmissionClick(this.searchQuery)">
    <button type="submit" @click="handleSubmissionClick(this.searchQuery)">Parse Word</button>
    <ParserOutput :parsedData="parsedData" :displayWord="displayWord" :errorMessage="errorMessage" />
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
      displayWord: this.searchQuery,
      parsedData: [],
      searchHistory: [],
      errorMessage: ''
    }
  },
  methods: {
    handleSubmissionClick(word){
      if (word) {
        this.errorMessage = ''
        this.fetchData(word)
        this.addToSearchHistory(word)
      }
    },
    fetchData(word) {
      apiService.parseWord(word)
        .then(response => {
          this.parsedData = response.data
          this.$emit('fetchedData', this.parsedData)
        })
        .catch(error => {
          console.error('Error fetching data:', error)
          this.errorMessage = `${this.displayWord} is not a Latin word. Please try another word.`
          return this.errorMessage
        })
        this.displayWord = word
      this.searchQuery = ''
    },
    addToSearchHistory(word) {
      if (!this.searchHistory.includes(word)) {
        if (!this.errorMessage) {
          this.searchHistory.unshift(word)
        }
      }
    },
  },
}
</script>
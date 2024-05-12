<template>
  <div class="parser-input">
    <SearchHistory :searchHistory="searchHistory" :handleSubmissionClick="handleSubmissionClick"/>
    <input type="text" v-model="searchQuery"
      placeholder="Enter Latin word here" @keydown.enter="handleSubmissionClick(this.searchQuery)">
    <button type="submit" @click="handleSubmissionClick(this.searchQuery)">Parse Word</button>
    <ParserOutput :parsedData="parsedData" :displayWord="displayWord" :errorMessage="errorMessage" />
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
      searchHistory: [],
      parsedData: [],
      errorMessage: ''
    }
  },
  methods: {
    handleSubmissionClick(word){
      if (word){
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
        })
        this.displayWord = word
        this.searchQuery = ''
    },
    addToSearchHistory(word){
      if (!this.searchHistory.includes(word)){
        this.searchHistory.unshift(word)
      }

    }
  },
}
</script>
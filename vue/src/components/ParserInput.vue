<template>
  <div class="parser-input">
    <input type="text" v-model="searchQuery" placeholder="Enter Latin word here"
      @keydown.enter="handleSubmissionClick(this.searchQuery)">
    <button type="submit" @click="handleSubmissionClick(this.searchQuery)">Parse Word</button>
  </div>
</template>

<script>
import { apiService } from '@/service/apiServices'

export default {
  name: 'ParserInput',
  data() {
    return {
      searchQuery: '',
      displayWord: this.searchQuery,
      parsedData: [],
      errorMessage: ''
    }
  },
  methods: {
    handleSubmissionClick(word) {
      if (word) {
        this.fetchData(word)
      }
    },
    fetchData(word) {
      apiService.parseWord(word)
        .then(response => {
          this.parsedData = response.data
          this.$emit('parsed-data-event', this.parsedData)
        })
        .catch(error => {
          console.error('Error fetching data:', error)
          this.errorMessage = `${this.displayWord} is not a Latin word. Please try another word.`
          this.$emit('sendErrorMessage', this.errorMessage)
        })
      this.displayWord = word
      this.searchQuery = ''
    },
  },
}
</script>
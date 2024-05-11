<template>
  <div class="parser-input">
    <input type="text" v-model="searchQuery" placeholder="Enter Latin word here">
    <button type="submit" @click="fetchData(this.searchQuery)">Parse Word</button>
    <ParserOutput :parsedData="parsedData" :displayWord="displayWord" :errorMessage="errorMessage" />
  </div>
</template>

<script>
import { apiService } from '@/service/apiServices'
import ParserOutput from '@/components/ParserOutput.vue'

export default {
  name: 'ParserInput',
  components: {
    ParserOutput
  },
  data() {
    return {
      searchQuery: '',
      displayWord: this.searchQuery,
      parsedData: [],
      errorMessage: ''
    }
  },
  methods: {
    fetchData(word) {
      apiService.parseWord(word)
        .then(response => {
          this.parsedData = response.data
          this.$emit('data-fetched', this.parsedData)
        })
        .catch(error => {
          console.error('Error fetching data:', error)
          this.errorMessage = `${this.displayWord} is not a Latin word. Please try another word.`
          console.log(this.errorMessage)
        })
        this.displayWord = word
        this.searchQuery = ''
    }
  },
}
</script>
<template>
  <div class="parser-input">
    <input type="text" v-model="searchQuery" placeholder="Enter your text">
    <button type="submit" @click="fetchData(this.searchQuery)">Parse Word</button>
    <ParserOutput :parsedData="parsedData"/>
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
      parsedData: []
    }
  },
  methods: {
    fetchData(word) {
      apiService.parseWord(word)
        .then(response => {
          this.parsedData = response.data
          console.log(this.parsedData)
          this.$emit('data-fetched', this.parsedData)
        })
        .catch(error => {
          console.error('Error fetching data:', error)
        })
    }
  },
}
</script>
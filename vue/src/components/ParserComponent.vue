<template>
  <div class="parser">
    <input type="text" v-model="searchQuery" placeholder="Enter your text">
    <button type="submit" v-on:click="fetchData()">Parse Word</button>
  </div>
</template>

<script>
import { apiService } from '@/service/apiServices'

export default {
  name: 'ParserComponent',
  data() {
    return {
      searchQuery: '',
      data: [] // Array to store fetched data from the backend
    }
  },
  methods: {
    fetchData() {
      apiService.parseWord(this.searchQuery)
        .then(response => {
          this.data = response.data // Update data array with fetched data
        })
        .catch(error => {
          console.error('Error fetching data:', error)
        })
    }
  }
}
</script>@/service/apiServices
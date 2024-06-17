<template>
  <div class="search-history">
    <nav v-if="searchHistory.length">
      <ul class="search-history-list">
        <h3>Search History</h3>
        <li v-for="(searchedWord, index) in searchHistory" :key="index"
          @click="parseWordFromHistory(searchedWord)">{{ searchedWord }}</li>
      </ul>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'SearchHistory',
  props: ['handleSubmissionClick', 'errorMessage'],
  data() {
    return {
      searchHistory: []
    }
  },
  methods: {
    parseWordFromHistory(word){
      this.handleSubmissionClick(word)
    }
  },
  addToSearchHistory(word) {
    if (!this.searchHistory.includes(word) && !this.errorMessage) {
      this.searchHistory.unshift(word)
    }
  }
}

</script>

<style>
.search-history-list {
  height: auto;
  width: 150px;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  overflow-x: hidden;
  list-style-type: none;
  background-color: #f2f2f2;
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

.search-history-list > li {
  padding-bottom: 5px;
  text-decoration: underline;
  color: blue;
}

li:hover {
  font-weight: bold;
}
</style>
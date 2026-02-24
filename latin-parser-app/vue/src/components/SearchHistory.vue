<template>
  <div class="search-history">
    <nav v-if="searchHistory.length">
      <ul class="search-history-list">
        <h3>Search History</h3>
        <li v-for="(searchedWord, index) in searchHistory" :key="index"
          @click="emit('parse-word', searchedWord)">{{ searchedWord }}</li>
      </ul>
    </nav>
  </div>
</template>

<script setup lang="ts">
const emit = defineEmits<{
  (e: 'parse-word', word: string): void
}>()

withDefaults(defineProps<{
  searchHistory: string[]
}>(), {
  searchHistory: () => []
})
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
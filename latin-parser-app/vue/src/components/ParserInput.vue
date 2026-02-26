<template>
  <div class="parser-input">
    <input type="text" v-model="searchQuery" placeholder="Enter Latin word here"
      @keydown.enter="submit">
    <button type="submit" @click="submit">Parse Word</button>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const emit = defineEmits<{
  (e: 'search', word: string): void
}>()

const searchQuery = ref('')

function submit(): void {
  if (searchQuery.value) {
    emit('search', searchQuery.value)
    searchQuery.value = ''
  }
}
</script>

<style scoped>
.parser-input {
  display: flex;
  justify-content: center;
  gap: 8px;
  padding: 16px 8px;
  flex-wrap: wrap;
}

input {
  min-height: 44px;
  font-size: 1rem;
  padding: 0 12px;
  width: 260px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  min-height: 44px;
  min-width: 80px;
  font-size: 1rem;
  padding: 0 16px;
  cursor: pointer;
}

@media (max-width: 768px) {
  input {
    width: 100%;
    max-width: 340px;
  }
}
</style>
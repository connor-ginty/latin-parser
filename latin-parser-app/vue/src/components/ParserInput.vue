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
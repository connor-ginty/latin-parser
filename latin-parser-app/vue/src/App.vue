
<template>
  <HeaderComponent />
  <router-view />
</template>

<script setup lang="ts">
import HeaderComponent from '@/components/HeaderComponent.vue'
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

@media (min-width: 769px) {
  #app {
    margin-left: 160px;
  }
}
</style>

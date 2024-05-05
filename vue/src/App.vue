
<template>
  <HeaderComponent :src="headerImage"/>
  <router-view />
</template>

<script>
import HeaderComponent from '@/components/HeaderComponent'

export default {
  components: {
    HeaderComponent
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
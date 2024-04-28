<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <ParserComponent/>
  </div>
</template>

<script>
// @ is an alias to /src
import ParserComponent from '@/components/ParserComponent.vue'

export default {
  name: 'ParserView',
  components: {
    ParserComponent
  }
}
</script>
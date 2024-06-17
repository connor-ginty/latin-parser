<template>
  <div class="parser-view">
    <ParserInput :searchQuery="searchQuery" />
    <ParserOutput :parsedData="parsedData" :displayWord="displayWord" :errorMessage="errorMessage"
      @parsed-data-event="handleParsedDataEvent" />
    <SearchHistory :handleSubmissionClick="handleSubmissionClick" />
  </div>
</template>

<script>
import ParserInput from '@/components/ParserInput.vue'
import ParserOutput from '@/components/ParserOutput.vue'
import SearchHistory from '@/components/SearchHistory.vue'

export default {
  name: 'ParserView',
  components: {
    SearchHistory,
    ParserInput,
    ParserOutput
  },
  props: ['searchQuery', 'searchHistory', 'errorMessage', 'displayWord'],
  methods: {
    handleParsedDataEvent() {
      return this.parsedData
    }
  }
}
</script>

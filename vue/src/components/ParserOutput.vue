<template>
  <div v-if="parsedData.length" class="parser-output">
    <h3 class="parsed-data" @data-fetched="handleParsedData">Parsed Data</h3>
    <ul v-for="(data, index) in parsedData" :key="index">
      <li>Part of Speech: {{ data.partOfSpeech }}</li>
      <li>Declension: {{ data.declension }}</li>
      <li>Gender: {{ data.gender }}</li>
      <li>Case: {{ data.case }}</li>
      <li>Number: {{ data.number }}</li>
      <li>Stem Type: {{ data.stemType }}</li>
      <li>Person: {{ data.person }}</li>
      <li>Tense: {{ data.tense }}</li>
      <li>Voice: {{ data.voice }}</li>
      <li>Mood: {{ data.mood }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'ParserOutput',
  props: ['parsedData']
}
</script>
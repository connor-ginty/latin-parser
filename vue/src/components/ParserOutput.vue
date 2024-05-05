<template>
  <div v-if="parsedData.length" class="parser-output">
    <h3 class="parsed-data">Results for "{{ displayWord }}"</h3>
    <table id="output-container-body">
      <thead>
        <tr>
          <th>Part of Speech</th>
          <th>Declension</th>
          <th>Gender</th>
          <th>Case</th>
          <th>Number</th>
          <th>Stem Type</th>
          <th>Person</th>
          <th>Tense</th>
          <th>Voice</th>
          <th>Mood</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(data, index) in parsedData" :key="index" class="parsed-row" @click="changeStyle">
          <td v-if="data.partOfSpeech">{{ data.partOfSpeech }}</td>
          <td v-else class="not-applicable" >N/A</td>
          <td v-if="data.declension">{{ data.declension }}</td>
          <td v-else class="not-applicable" >N/A</td>
          <td v-if="data.gender">{{ data.gender }}</td>
          <td v-else class="not-applicable" >N/A</td>
          <td v-if="data.case">{{ data.case }}</td>
          <td v-else class="not-applicable" >N/A</td>
          <td v-if="data.number">{{ data.number }}</td>
          <td v-else class="not-applicable" >N/A</td>
          <td v-if="data.stemType">{{ data.stemType }}</td>
          <td v-else class="not-applicable" >N/A</td>
          <td v-if="data.person">{{ data.person }}</td>
          <td v-else class="not-applicable" >N/A</td>
          <td v-if="data.tense">{{ data.tense }}</td>
          <td v-else class="not-applicable" >N/A</td>
          <td v-if="data.voice">{{ data.voice }}</td>
          <td v-else class="not-applicable" >N/A</td>
          <td v-if="data.mood">{{ data.mood }}</td>
          <td v-else class="not-applicable" >N/A</td>
        </tr>
      </tbody>
      <br>
    </table>
  </div>
</template>

<script>
export default {
  name: 'ParserOutput',
  props: ['displayWord','parsedData'],
}
</script>

<style scoped>
div.parser-output {
  padding-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

table {
  border-collapse: collapse;
  width: 65%;
}

th {
  background-color: #f2f2f2;
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}

tr:hover {
  background-color: #f2f2f2;
}

td.not-applicable {
  background-color: #f77c7c;
}
</style>
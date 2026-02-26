<template>
  <div v-if="parsedData.length && !errorMessage" class="parser-output">
    <h3 class="parsed-data">{{ resultsMessage }}</h3>
    <div class="table-scroll">
      <table id="output-container-body">
        <thead>
          <tr>
            <th v-for="col in columns" :key="col.key">{{ col.label }}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(data, index) in parsedData" :key="index" class="parsed-row">
            <td v-for="col in columns" :key="col.key"
              :class="{ 'not-applicable': !data[col.key] }">
              {{ data[col.key] || 'N/A' }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div v-else-if="errorMessage" class="error-output">
    <p>{{ errorMessage }}</p>
  </div>
</template>

<script setup lang="ts">
import { ParsedData } from '@/types/index'

withDefaults(defineProps<{
  parsedData: ParsedData[]
  resultsMessage: string
  errorMessage: string
}>(), {
  parsedData: () => [],
  resultsMessage: '',
  errorMessage: ''
})

const columns: { label: string; key: keyof ParsedData }[] = [
  { label: 'Part of Speech', key: 'partOfSpeech' },
  { label: 'Declension',     key: 'declension' },
  { label: 'Gender',         key: 'gender' },
  { label: 'Case',           key: 'case' },
  { label: 'Number',         key: 'number' },
  { label: 'Stem Type',      key: 'stemType' },
  { label: 'Person',         key: 'person' },
  { label: 'Tense',          key: 'tense' },
  { label: 'Voice',          key: 'voice' },
  { label: 'Mood',           key: 'mood' },
]
</script>

<style scoped>
div.parser-output {
  padding-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.table-scroll {
  width: 65%;
  overflow-x: auto;
}

table {
  border-collapse: collapse;
  width: 100%;
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

.error-output {
  height: auto;
  padding: 20px;
  font-size: large;
}

@media (max-width: 768px) {
  .table-scroll {
    width: 100%;
  }
  th, td {
    padding: 6px;
    font-size: 0.8rem;
  }
}
</style>

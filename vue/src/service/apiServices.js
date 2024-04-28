import axios from 'axios'
import endpoints from './endpoints'

export const apiService = {
  parseWord(word) {
    return axios.get(endpoints.parseWord + word);
  },
  getDefinition(word) {
    return axios.put(endpoints.getDefinition + word);
  }
}
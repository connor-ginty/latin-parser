import axios from 'axios'
import endpoints from './endpoints'

export const apiService = {
  parseWord: function (word) {
    return axios.get(endpoints.parseWord + word)
  },
  getDefinition: function(word) {
    return axios.put(endpoints.getDefinition + word)
  }
}
import axios from 'axios'
import endpoints from './endpoints'

export const apiService = {
  parseWord: async function (word) {
    return await axios.get(endpoints.parseWord + word)
  },
  getSearchHistory: function(){
    return axios.get(endpoints.getSearchHistory)
  },
}
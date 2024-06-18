import axios from 'axios'
import endpoints from './endpoints'

export const apiService = {
  parseWord: function (word) {
    return axios.get(endpoints.parseWord + word)
  },
  getSearchHistory: function(){
    return axios.get(endpoints.getSearchHistory)
  },
}
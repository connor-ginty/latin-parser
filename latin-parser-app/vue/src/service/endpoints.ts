const baseURL = process.env.VUE_APP_API_BASE_URL || 'http://localhost:8080'

export default {
  parseWord: `${baseURL}/service/latin/parser/`
}

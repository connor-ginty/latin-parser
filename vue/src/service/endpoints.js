const baseURL = process.env.VUE_APP_BASE_URL;

export const endpoints = {
  parseWord: `${baseURL}/latinParser/v1/parseWord?latinWord=`,
  getDefinition: ''
}
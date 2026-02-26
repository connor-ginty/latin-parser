import axios from 'axios'
import type { AxiosResponse } from 'axios'
import type { ParseResponse } from '@/types'
import endpoints from './endpoints'

export const apiService = {
  parseWord: (word: string): Promise<AxiosResponse<ParseResponse>> => {
    return axios.get<ParseResponse>(endpoints.parseWord + word)
  }
}

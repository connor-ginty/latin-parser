import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import ParserView from '@/views/ParserView.vue'
import ParserInput from '@/components/ParserInput.vue'
import ParserOutput from '@/components/ParserOutput.vue'
import SearchHistory from '@/components/SearchHistory.vue'
import { apiService } from '@/service/apiServices'
import axios from 'axios'
import type { ParsedData } from '@/types'

vi.mock('@/service/apiServices', () => ({
  apiService: {
    parseWord: vi.fn(),
  },
}))

vi.mock('axios', async (importOriginal) => {
  const actual = await importOriginal<typeof import('axios')>()
  return {
    ...actual,
    default: {
      ...actual.default,
      isAxiosError: vi.fn(),
    },
  }
})

const mockInflections: ParsedData[] = [
  { partOfSpeech: 'noun', declension: '1st', gender: 'feminine', case: 'nominative', number: 'singular' },
]

const mockParseWordSuccess = () =>
  vi.mocked(apiService.parseWord).mockResolvedValue({
    data: { success: true, message: 'Results for amor', inflections: mockInflections },
  } as any)

describe('ParserView', () => {
  beforeEach(() => {
    vi.clearAllMocks()
    vi.mocked(axios.isAxiosError).mockReturnValue(false)
  })

  describe('API integration', () => {
    it('calls apiService.parseWord with the searched word', async () => {
      mockParseWordSuccess()
      const wrapper = mount(ParserView)
      wrapper.findComponent(ParserInput).vm.$emit('search', 'amor')
      await flushPromises()
      expect(apiService.parseWord).toHaveBeenCalledWith('amor')
    })

    it('sets parsedData from response.data.inflections', async () => {
      mockParseWordSuccess()
      const wrapper = mount(ParserView)
      wrapper.findComponent(ParserInput).vm.$emit('search', 'amor')
      await flushPromises()
      expect(wrapper.findComponent(ParserOutput).props('parsedData')).toEqual(mockInflections)
    })

    it('sets resultsMessage from response.data.message', async () => {
      mockParseWordSuccess()
      const wrapper = mount(ParserView)
      wrapper.findComponent(ParserInput).vm.$emit('search', 'amor')
      await flushPromises()
      expect(wrapper.findComponent(ParserOutput).props('resultsMessage')).toBe('Results for amor')
    })

    it('clears errorMessage on a successful search', async () => {
      mockParseWordSuccess()
      const wrapper = mount(ParserView)
      wrapper.findComponent(ParserInput).vm.$emit('search', 'amor')
      await flushPromises()
      expect(wrapper.findComponent(ParserOutput).props('errorMessage')).toBe('')
    })
  })

  describe('search history management', () => {
    it('adds the searched word to the front of searchHistory', async () => {
      mockParseWordSuccess()
      const wrapper = mount(ParserView)
      wrapper.findComponent(ParserInput).vm.$emit('search', 'amor')
      await flushPromises()
      const history = wrapper.findComponent(SearchHistory).props('searchHistory') as string[]
      expect(history[0]).toBe('amor')
    })

    it('does not add duplicate words to searchHistory', async () => {
      mockParseWordSuccess()
      const wrapper = mount(ParserView)
      wrapper.findComponent(ParserInput).vm.$emit('search', 'amor')
      await flushPromises()
      wrapper.findComponent(ParserInput).vm.$emit('search', 'amor')
      await flushPromises()
      const history = wrapper.findComponent(SearchHistory).props('searchHistory') as string[]
      expect(history.filter(w => w === 'amor').length).toBe(1)
    })

    it('limits searchHistory to 50 items', async () => {
      const wrapper = mount(ParserView)
      for (let i = 0; i < 51; i++) {
        vi.mocked(apiService.parseWord).mockResolvedValue({
          data: { success: true, message: '', inflections: [] },
        } as any)
        wrapper.findComponent(ParserInput).vm.$emit('search', `word${i}`)
        await flushPromises()
      }
      const history = wrapper.findComponent(SearchHistory).props('searchHistory') as string[]
      expect(history.length).toBeLessThanOrEqual(50)
    })

    it('re-searches when SearchHistory emits "parse-word"', async () => {
      mockParseWordSuccess()
      const wrapper = mount(ParserView)
      wrapper.findComponent(SearchHistory).vm.$emit('parse-word', 'puella')
      await flushPromises()
      expect(apiService.parseWord).toHaveBeenCalledWith('puella')
    })
  })

  describe('error handling', () => {
    it('sets errorMessage from error.response.data.message on AxiosError', async () => {
      const axiosError = { response: { data: { message: 'Word not found' } } }
      vi.mocked(apiService.parseWord).mockRejectedValue(axiosError)
      vi.mocked(axios.isAxiosError).mockReturnValue(true)

      const wrapper = mount(ParserView)
      wrapper.findComponent(ParserInput).vm.$emit('search', 'xyz')
      await flushPromises()

      expect(wrapper.findComponent(ParserOutput).props('errorMessage')).toBe('Word not found')
    })

    it('sets a generic errorMessage on non-Axios errors', async () => {
      vi.mocked(apiService.parseWord).mockRejectedValue(new Error('Network failure'))
      vi.mocked(axios.isAxiosError).mockReturnValue(false)

      const wrapper = mount(ParserView)
      wrapper.findComponent(ParserInput).vm.$emit('search', 'xyz')
      await flushPromises()

      expect(wrapper.findComponent(ParserOutput).props('errorMessage')).toBe(
        'An unexpected error occurred. Please try again.'
      )
    })
  })
})

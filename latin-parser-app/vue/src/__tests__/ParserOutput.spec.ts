import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import ParserOutput from '@/components/ParserOutput.vue'
import type { ParsedData } from '@/types'
import { setViewport, MOBILE_WIDTH, DESKTOP_WIDTH } from './setup'

const COLUMN_LABELS = [
  'Part of Speech', 'Declension', 'Gender', 'Case', 'Number',
  'Stem Type', 'Person', 'Tense', 'Voice', 'Mood',
]

const partialRow: ParsedData = {
  partOfSpeech: 'noun',
  declension: '1st',
  gender: 'feminine',
  case: 'nominative',
  number: 'singular',
  // stemType, person, tense, voice, mood are undefined
}

const fullRow: ParsedData = {
  partOfSpeech: 'verb',
  declension: undefined,
  gender: undefined,
  case: undefined,
  number: 'singular',
  stemType: undefined,
  person: '1st',
  tense: 'present',
  voice: 'active',
  mood: 'indicative',
}

function mountWith(overrides: Partial<{ parsedData: ParsedData[], resultsMessage: string, errorMessage: string }>) {
  return mount(ParserOutput, {
    props: {
      parsedData: [],
      resultsMessage: '',
      errorMessage: '',
      ...overrides,
    },
  })
}

describe('ParserOutput', () => {
  describe('empty state', () => {
    it('renders neither table nor error when parsedData is empty and no error', () => {
      const wrapper = mountWith({})
      expect(wrapper.find('.parser-output').exists()).toBe(false)
      expect(wrapper.find('.error-output').exists()).toBe(false)
    })
  })

  describe('table rendering', () => {
    it('shows .parser-output with a table when parsedData has rows', () => {
      const wrapper = mountWith({ parsedData: [partialRow] })
      expect(wrapper.find('.parser-output').exists()).toBe(true)
      expect(wrapper.find('table').exists()).toBe(true)
    })

    it('renders exactly 10 column headers', () => {
      const wrapper = mountWith({ parsedData: [partialRow] })
      const headers = wrapper.findAll('th')
      expect(headers.length).toBe(10)
    })

    it('renders the correct column header labels', () => {
      const wrapper = mountWith({ parsedData: [partialRow] })
      const headers = wrapper.findAll('th').map(h => h.text())
      expect(headers).toEqual(COLUMN_LABELS)
    })

    it('renders populated cell values from parsedData', () => {
      const wrapper = mountWith({ parsedData: [partialRow] })
      const cells = wrapper.findAll('td').map(td => td.text())
      expect(cells).toContain('noun')
      expect(cells).toContain('1st')
      expect(cells).toContain('feminine')
    })

    it('renders "N/A" for undefined fields', () => {
      const wrapper = mountWith({ parsedData: [partialRow] })
      const naCells = wrapper.findAll('td').filter(td => td.text() === 'N/A')
      expect(naCells.length).toBeGreaterThan(0)
    })

    it('applies "not-applicable" class to cells with undefined values', () => {
      const wrapper = mountWith({ parsedData: [partialRow] })
      expect(wrapper.find('td.not-applicable').exists()).toBe(true)
    })

    it('does NOT apply "not-applicable" class to populated cells', () => {
      const wrapper = mountWith({ parsedData: [fullRow] })
      const populatedCells = wrapper.findAll('td').filter(td => td.text() !== 'N/A')
      populatedCells.forEach(cell => {
        expect(cell.classes()).not.toContain('not-applicable')
      })
    })

    it('displays resultsMessage in .parsed-data heading', () => {
      const wrapper = mountWith({ parsedData: [partialRow], resultsMessage: 'Results for amor' })
      expect(wrapper.find('.parsed-data').text()).toBe('Results for amor')
    })
  })

  describe('error state', () => {
    it('shows .error-output when errorMessage is set', () => {
      const wrapper = mountWith({ errorMessage: 'Word not found' })
      expect(wrapper.find('.error-output').exists()).toBe(true)
      expect(wrapper.find('.error-output').text()).toContain('Word not found')
    })

    it('does not show the table when errorMessage is set', () => {
      const wrapper = mountWith({ parsedData: [partialRow], errorMessage: 'Error' })
      expect(wrapper.find('.parser-output').exists()).toBe(false)
    })
  })

  describe('responsive structure', () => {
    it('table is wrapped in a .table-scroll div (enables mobile horizontal scroll)', () => {
      const wrapper = mountWith({ parsedData: [partialRow] })
      expect(wrapper.find('.table-scroll').exists()).toBe(true)
      expect(wrapper.find('.table-scroll table').exists()).toBe(true)
    })

    it('desktop: .table-scroll is present and contains the table', () => {
      setViewport(DESKTOP_WIDTH)
      const wrapper = mountWith({ parsedData: [partialRow] })
      expect(wrapper.find('.table-scroll table').exists()).toBe(true)
    })

    it('mobile: .table-scroll still wraps the table', () => {
      setViewport(MOBILE_WIDTH)
      const wrapper = mountWith({ parsedData: [partialRow] })
      expect(wrapper.find('.table-scroll table').exists()).toBe(true)
    })
  })
})

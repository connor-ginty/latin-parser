import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import SearchHistory from '@/components/SearchHistory.vue'
import { setViewport, MOBILE_WIDTH, DESKTOP_WIDTH } from './setup'

describe('SearchHistory', () => {
  describe('empty state', () => {
    it('does not render <nav> when searchHistory is empty', () => {
      const wrapper = mount(SearchHistory, { props: { searchHistory: [] } })
      expect(wrapper.find('nav').exists()).toBe(false)
    })
  })

  describe('populated state', () => {
    it('renders <nav> when searchHistory has items', () => {
      const wrapper = mount(SearchHistory, { props: { searchHistory: ['amor'] } })
      expect(wrapper.find('nav').exists()).toBe(true)
    })

    it('renders one <li> per search word', () => {
      const words = ['amor', 'puella', 'amo']
      const wrapper = mount(SearchHistory, { props: { searchHistory: words } })
      expect(wrapper.findAll('li').length).toBe(3)
    })

    it('displays each word in its list item', () => {
      const words = ['amor', 'puella']
      const wrapper = mount(SearchHistory, { props: { searchHistory: words } })
      const items = wrapper.findAll('li')
      expect(items[0].text()).toBe('amor')
      expect(items[1].text()).toBe('puella')
    })

    it('emits "parse-word" with the word when a list item is clicked', async () => {
      const wrapper = mount(SearchHistory, { props: { searchHistory: ['amor', 'puella'] } })
      await wrapper.findAll('li')[1].trigger('click')
      expect(wrapper.emitted('parse-word')).toBeTruthy()
      expect(wrapper.emitted('parse-word')![0]).toEqual(['puella'])
    })
  })

  describe('responsive structure', () => {
    it('has .search-history-list class for CSS responsive targeting', () => {
      const wrapper = mount(SearchHistory, { props: { searchHistory: ['amor'] } })
      expect(wrapper.find('.search-history-list').exists()).toBe(true)
    })

    it('desktop: list items are rendered inside .search-history-list', () => {
      setViewport(DESKTOP_WIDTH)
      const wrapper = mount(SearchHistory, { props: { searchHistory: ['amor'] } })
      expect(wrapper.find('.search-history-list li').exists()).toBe(true)
    })

    it('mobile: list items remain inside .search-history-list', () => {
      setViewport(MOBILE_WIDTH)
      const wrapper = mount(SearchHistory, { props: { searchHistory: ['amor'] } })
      expect(wrapper.find('.search-history-list li').exists()).toBe(true)
    })
  })
})

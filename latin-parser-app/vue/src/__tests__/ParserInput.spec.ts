import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import ParserInput from '@/components/ParserInput.vue'
import { setViewport, MOBILE_WIDTH, DESKTOP_WIDTH } from './setup'

describe('ParserInput', () => {
  describe('structure', () => {
    it('renders the container, input, and button', () => {
      const wrapper = mount(ParserInput)
      expect(wrapper.find('.parser-input').exists()).toBe(true)
      expect(wrapper.find('input').exists()).toBe(true)
      expect(wrapper.find('button').exists()).toBe(true)
    })

    it('input has the correct placeholder', () => {
      const wrapper = mount(ParserInput)
      expect(wrapper.find('input').attributes('placeholder')).toBe('Enter Latin word here')
    })
  })

  describe('functionality', () => {
    it('emits "search" with the word when button is clicked', async () => {
      const wrapper = mount(ParserInput)
      await wrapper.find('input').setValue('amor')
      await wrapper.find('button').trigger('click')
      expect(wrapper.emitted('search')).toBeTruthy()
      expect(wrapper.emitted('search')![0]).toEqual(['amor'])
    })

    it('emits "search" with the word on Enter keydown', async () => {
      const wrapper = mount(ParserInput)
      await wrapper.find('input').setValue('puella')
      await wrapper.find('input').trigger('keydown.enter')
      expect(wrapper.emitted('search')).toBeTruthy()
      expect(wrapper.emitted('search')![0]).toEqual(['puella'])
    })

    it('does not emit "search" when input is empty', async () => {
      const wrapper = mount(ParserInput)
      await wrapper.find('button').trigger('click')
      expect(wrapper.emitted('search')).toBeFalsy()
    })

    it('clears the input field after submitting', async () => {
      const wrapper = mount(ParserInput)
      await wrapper.find('input').setValue('amor')
      await wrapper.find('button').trigger('click')
      expect((wrapper.find('input').element as HTMLInputElement).value).toBe('')
    })
  })

  describe('desktop layout structure', () => {
    it('input is a child of .parser-input flex container', () => {
      setViewport(DESKTOP_WIDTH)
      const wrapper = mount(ParserInput)
      const container = wrapper.find('.parser-input')
      expect(container.find('input').exists()).toBe(true)
      expect(container.find('button').exists()).toBe(true)
    })
  })

  describe('mobile layout structure', () => {
    it('same input and button are present at mobile viewport', () => {
      setViewport(MOBILE_WIDTH)
      const wrapper = mount(ParserInput)
      expect(wrapper.find('input').exists()).toBe(true)
      expect(wrapper.find('button').exists()).toBe(true)
    })
  })
})

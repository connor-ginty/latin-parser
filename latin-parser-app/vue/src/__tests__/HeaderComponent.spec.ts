import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import HeaderComponent from '@/components/HeaderComponent.vue'

describe('HeaderComponent', () => {
  it('renders a <header> element', () => {
    const wrapper = mount(HeaderComponent)
    expect(wrapper.find('header').exists()).toBe(true)
  })

  it('displays the correct title in <h1>', () => {
    const wrapper = mount(HeaderComponent)
    expect(wrapper.find('h1').text()).toBe('Lorum Ipsum Et Cetera')
  })

  it('renders an image with the header-image class', () => {
    const wrapper = mount(HeaderComponent)
    expect(wrapper.find('img.header-image').exists()).toBe(true)
  })

  it('image has descriptive alt text', () => {
    const wrapper = mount(HeaderComponent)
    expect(wrapper.find('img').attributes('alt')).toBeTruthy()
  })

  it('is wrapped in .parser-app-header', () => {
    const wrapper = mount(HeaderComponent)
    expect(wrapper.find('.parser-app-header').exists()).toBe(true)
  })
})

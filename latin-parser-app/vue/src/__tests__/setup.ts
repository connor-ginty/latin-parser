// jsdom does not implement window.matchMedia — provide a minimal stub so
// components that reference it (or libraries that do) mount without errors.
Object.defineProperty(window, 'matchMedia', {
  writable: true,
  value: (query: string) => ({
    matches: false,
    media: query,
    onchange: null,
    addListener: () => {},
    removeListener: () => {},
    addEventListener: () => {},
    removeEventListener: () => {},
    dispatchEvent: () => false,
  }),
})

/**
 * Simulate a viewport width change for layout tests.
 * Note: jsdom does not execute CSS media queries, so this only affects
 * window.innerWidth — useful if components use JS-based breakpoint logic.
 * CSS media-query rendering is verified visually in the browser.
 */
export function setViewport(width: number): void {
  Object.defineProperty(window, 'innerWidth', {
    writable: true,
    configurable: true,
    value: width,
  })
  window.dispatchEvent(new Event('resize'))
}

export const MOBILE_WIDTH = 375   // iPhone SE
export const DESKTOP_WIDTH = 1280

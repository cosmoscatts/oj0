export function clearWindowUrlParams() {
  let url = window.location.href
  if (url.includes('?')) {
    url = url.replace(/(\?|#)[^'"]*/, '')
    window.history.pushState({}, '', url)
  }
}

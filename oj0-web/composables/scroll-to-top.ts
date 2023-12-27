export function scrollTop(id: string) {
  const element = document.getElementById(id)
  if (!element)
    return
  element.scrollTo({
    behavior: 'smooth',
    top: 0,
    left: 0,
  })
}

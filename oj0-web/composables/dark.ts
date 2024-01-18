export const isDark = computed(() => {
  const colorMode = useColorMode()
  return colorMode.value === 'dark'
})
export function toggleDark() {
  const colorMode = useColorMode()
  colorMode.preference = colorMode.value === 'dark' ? 'light' : 'dark'
}

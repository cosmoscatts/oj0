export async function setThemeColor() {
  await setPrimaryColor()
  Array.from({ length: 10 }).forEach((_, index) => {
    document.body.style.setProperty(`--primary-${index + 1}`, `var(--oj-primary-${index + 1})`)
    document.body.style.setProperty(`--warning-${index + 1}`, `var(--oj-warning-${index + 1})`)
    document.body.style.setProperty(`--danger-${index + 1}`, `var(--oj-danger-${index + 1})`)
    document.body.style.setProperty(`--success-${index + 1}`, `var(--oj-success-${index + 1})`)
  })
}

export async function setPrimaryColor() {
  const themes = await import('~/constants/themes.json').then(r => r.default) as [string, ThemeColors, string[]][]
  const settings = useUserSettings()
  const currentTheme = settings.value.themeColors?.['--theme-color-name'] || themes[0][1]['--theme-color-name']
  const primaryColors = themes.find(i => i[0] === currentTheme)?.[2]
  if (!primaryColors?.length)
    return
  const html = document.documentElement
  primaryColors.forEach((i, idx) => html.style.setProperty(`--oj-primary-${idx + 1}`, i))
}

export function setThemeColor() {
  Array.from({ length: 10 }).forEach((_, index) => {
    const targetIndex = isDark.value ? 9 - index : index + 1
    document.body.style.setProperty(`--primary-${index + 1}`, `var(--oj-primary-${targetIndex})`)
    document.body.style.setProperty(`--warning-${index + 1}`, `var(--oj-warning-${targetIndex})`)
    document.body.style.setProperty(`--danger-${index + 1}`, `var(--oj-danger-${targetIndex})`)
    document.body.style.setProperty(`--success-${index + 1}`, `var(--oj-success-${targetIndex})`)
  })
}

watch(isDark, setThemeColor)

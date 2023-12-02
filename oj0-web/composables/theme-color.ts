export function setThemeColor() {
  Array.from({ length: 10 }).forEach((_, index) => {
    document.body.style.setProperty(`--primary-${index + 1}`, `var(--oj-primary-${index + 1})`)
    document.body.style.setProperty(`--warning-${index + 1}`, `var(--oj-warning-${index + 1})`)
    document.body.style.setProperty(`--danger-${index + 1}`, `var(--oj-danger-${index + 1})`)
    document.body.style.setProperty(`--success-${index + 1}`, `var(--oj-success-${index + 1})`)
  })
}

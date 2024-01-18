export function getProfileYearOptionData() {
  const now = dayjs()
  const year = now.format('YYYY')
  const yearData = [] as string[]

  for (let i = Number(year); i >= 2023; i--)
    yearData.push(String(i))

  const yearOptions = Array.from({ length: yearData.length }, (_, idx) => {
    return {
      value: yearData[idx],
      label: yearData[idx],
    }
  })

  return {
    year,
    yearData,
    yearOptions,
  }
}

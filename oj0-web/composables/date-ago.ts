/**
 * 多少 x 前
 */
export function formatDateAgo(date?: string | Date | number | null) {
  if (!date)
    return ''

  const ONE_MINUTE = 60 * 1000
  const ONE_HOUR = ONE_MINUTE * 60
  const ONE_DAY = ONE_HOUR * 24
  const ONE_WEEK = 7 * ONE_DAY
  const ONE_MONTH = ONE_DAY * 30
  const ONE_YEAR = ONE_MONTH * 12

  const now = dayjs().valueOf()
  const time = dayjs(date).valueOf()
  const diff = now - time

  if (diff < 0)
    return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
  if (diff < ONE_MINUTE)
    return '刚刚'
  if (diff < ONE_HOUR) { // 多少分钟前
    const minutes = ~~(diff / ONE_MINUTE)
    return `${minutes}分钟前`
  }
  if (diff < ONE_DAY) { // 多少小时前
    const hours = ~~(diff / ONE_HOUR)
    return `${hours}小时前`
  }
  if (diff < ONE_WEEK) { // 多少天前
    const days = ~~(diff / ONE_DAY)
    return `${days}天前`
  }
  if (diff < ONE_MONTH) { // 多少周前
    const weeks = ~~(diff / ONE_WEEK)
    return `${weeks}周前`
  }
  if (diff < ONE_YEAR) { // 多少月前
    const months = ~~(diff / ONE_MONTH)
    return `${months}月前`
  }
  const years = ~~(diff / ONE_YEAR) // 多少年前
  return `${years}年前`
}

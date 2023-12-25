/**
 * 判断题目是否通过
 */
export function checkQuestionAccepted(record: QuestionSubmit) {
  const status = record.status
  if (status !== 2)
    return false
  const message = record.judgeInfo?.message || ''
  if (message !== 'Accepted')
    return false
  return true
}

/**
 * 判断是否是自己的提交记录
 */
export function checkMySubmitRecord(record: QuestionSubmit) {
  const authStore = useAuthStore()
  const userId = authStore.user?.id
  if (!record.userId)
    return false
  return record.userId === userId
}

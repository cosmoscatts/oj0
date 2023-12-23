export function checkQuestionAccepted(record: QuestionSubmit) {
  const status = record.status
  if (status !== 2)
    return false
  const message = record.judgeInfo?.message || ''
  if (message !== 'Accepted')
    return false
  return true
}

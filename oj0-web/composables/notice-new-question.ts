import { useToast } from 'vue-toastification'

/**
 * 当今天有新题目发布，且该用户没有所有新题目的提交记录，
 * 且用户当前不在【题库】或者【做题】页面时，提示用户有新题目
 */
export async function doNewQuestionNotification(userId: number) {
  const dateRange = getNowDateRange()
  const { data: { records } } = await QuestionApi.listVo({
    pageSize: -1,
    ...dateRange,
  })
  if (!records?.length)
    return

  const newQuestionIds = records.map(i => i.id)
  // 查询用户今天的提交记录
  const { data: { records: submitRecords } } = await QuestionSubmitApi.list({
    userId,
    ...dateRange,
  })

  if (submitRecords?.length) {
    // 判断今天的提交记录里是否有所有的新题目
    const submitQuestionIds = submitRecords.map(i => i.questionId)
    let flag = true
    for (const id of newQuestionIds) {
      if (!submitQuestionIds.includes(id)) {
        flag = false
        break
      }
    }
    if (flag)
      return
  }

  const route = useRoute()
  if (route.path === '/questions')
    return
  if (route.path.startsWith('/resolve'))
    return

  const toast = useToast()
  toast.info('今天有新题目发布哦，快去看看吧～')
}

function getNowDateRange() {
  const now = dayjs()
  const str = now.format('YYYY-MM-DD')
  const [createTimeMin, createTimeMax] = [`${str} 00:00:00`, `${str} 23:59:59`]
  return {
    createTimeMin,
    createTimeMax,
  }
}

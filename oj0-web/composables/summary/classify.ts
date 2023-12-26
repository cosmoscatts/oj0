import { commonQuestionGroupTags } from '~/config/question'

const defaultClassification = {
  基本: [],
  算法: [],
  数据结构: [],
  技巧: [],
  数学: [],
  其他: [],
}

export async function getQuestionClassification() {
  const { data: { records } } = await QuestionApi.listVo({ pageSize: -1 })
  if (!records?.length)
    return defaultClassification

  const classificationMap = commonQuestionGroupTags.reduce((prev, cur) => {
    const key = cur[0] as string
    prev[key] = cur[1] as string[]
    return prev
  }, {} as Record<string, string[]>)
}

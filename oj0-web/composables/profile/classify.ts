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

  /**
   * 基础数据结构/高级数据结构 合并为数据结构
   */
  const classificationMap = commonQuestionGroupTags.reduce((prev, cur) => {
    const value = ['基础数据结构', '高级数据结构'].includes(cur[0] as string)
      ? '数据结构'
      : cur[0] as string
    for (const key of cur[1])
      prev[key] = value
    return prev
  }, {} as Record<string, string>)

  return records.reduce((prev, cur) => {
    const id = String(cur.id!)
    const tags = cur.tags
    if (tags?.length) {
      for (const tag of tags) {
        if (!Object.keys(classificationMap).includes(tag))
          continue
        const parentTag = classificationMap[tag]
        let temp = [parentTag] as string[]
        if (Object.keys(prev).includes(String(id)))
          temp = [...prev[id], parentTag]
        prev[id] = [...new Set(temp)]
      }
    }
    return prev
  }, {} as Record<string, string[]>)
}

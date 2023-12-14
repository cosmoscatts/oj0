/**
 * 将 Question => 转成 VO
 *
 * tags/judgeCase/judgeConfig 等需要特殊处理
 */
export function transformQuestionToVO(records?: QuestionDO[]): Question[] {
  if (!records?.length)
    return []

  const transform = (item: QuestionDO) => {
    return {
      ...item,
      tags: item.tags ? JSON.parse(item.tags) : [],
      judgeConfig: item.judgeConfig ? JSON.parse(item.judgeConfig) : {},
      judgeCase: item.judgeCase ? JSON.parse(item.judgeCase) : [],
    }
  }

  return records.map(i => transform(i))
}

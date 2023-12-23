/**
 * 枚举一些常用的下拉框选项，数据不需要通过后端查询
 */

import type { SelectOptionData, SelectOptionGroup } from '@arco-design/web-vue'
import { ACCESS_ENUM, QUESTION_DIFFICULTY_ENUM, RESOLVE_LANGUAGE_ENUM } from '~/constants'
import { commonQuestionGroupTags, commonQuestionTags } from '~/config/question'

/**
 * 用户角色下拉框
 */
export const userRoleOptions = [ACCESS_ENUM.ADMIN, ACCESS_ENUM.USER].map((i, idx) => {
  return {
    value: i,
    label: ['管理员', '用户'][idx],
  }
})

/**
 * 题目难度下拉框
 */
export const questionDifficultyOptions = Array.from({ length: 3 }, (_, idx) => {
  return {
    value: [QUESTION_DIFFICULTY_ENUM.EASY, QUESTION_DIFFICULTY_ENUM.MEDIUM, QUESTION_DIFFICULTY_ENUM.HARD][idx],
    label: ['简单', '中等', '困难'][idx],
  }
})

/**
 * 题目标签下拉框
 */
function createQuestionTagOptions() {
  return commonQuestionTags.map((i) => {
    return {
      value: i,
      label: i,
    }
  })
}
export const questionTagOptions = createQuestionTagOptions()

/**
 * 题目标签下拉框（分组）
 */
function createQuestionTagGroupOptions(): SelectOptionGroup[] {
  return commonQuestionGroupTags.map((i) => {
    const [label, tags] = i
    return {
      isGroup: true,
      label,
      options: (tags as string[]).map((j) => {
        return {
          value: j,
          label: j,
        } as SelectOptionData
      }),
    } as SelectOptionGroup
  })
}
export const questionTagGroupOptions = createQuestionTagGroupOptions()

/**
 * 做题语言类型
 */
export const questionResolveLanguageOptions = Array.from({ length: 3 }, (_, idx) => {
  return {
    value: [RESOLVE_LANGUAGE_ENUM.JAVA, RESOLVE_LANGUAGE_ENUM.CPP, RESOLVE_LANGUAGE_ENUM.GO][idx],
    label: ['Java', 'C++', 'Go'][idx],
  }
})

/**
 * 题目提交结果
 */
export const questionSubmitStatusOptions = Array.from({ length: 4 }, (_, idx) => {
  return {
    value: idx,
    label: ['等待中', '判题中', '通过', '解答错误'][idx],
  }
})

/**
 * 根据 value 获取 label
 */
export function getOptionsLabel(options?: SelectOptionData[], value?: string | number) {
  if (value == null)
    return ''

  options ||= []
  return options.find(i => i.value === value)?.label || ''
}

<script setup lang="ts">
import { QUESTION_DIFFICULTY_ENUM } from '~/constants'

const { userId } = defineProps<{
  userId?: string | number
}>()

const questions = ref<Question[]>([])
const acceptedData = ref<QuestionSubmit[]>([])

/**
 * 查询所有的题目信息，初始化时查询一次即可
 */
async function fetchQuestionData() {
  const { data: { records } } = await QuestionApi.listVo({})
  questions.value = records || []
}
fetchQuestionData()

/**
 * 查询所有判题成功的提交信息并过滤出通过的信息
 */
async function fetchAcceptedData() {
  if (!userId)
    return
  const { data: { records } } = await QuestionSubmitApi.list({
    userId,
    status: 2,
    sortField: 'createTime',
    sortOrder: 'descend',
  })
  acceptedData.value = records?.filter(i => i.judgeInfo && i.judgeInfo?.message === 'Accepted').slice(0, 10) || []
}
fetchAcceptedData()
watch(() => userId, fetchAcceptedData)

const questionDifficultyMap = computed(() => {
  if (!questions.value?.length)
    return {}
  return questions.value.reduce((prev, cur) => {
    if (cur.id && cur.difficulty)
      prev[cur.id] = cur.difficulty

    return prev
  }, {} as Record<number, string>)
})

const totalQuestionNum = computed(() => questions.value?.length || 0)
const easyQuestionNum = computed(() => {
  if (!questions.value?.length)
    return 0
  return questions.value.filter(i => i.difficulty === QUESTION_DIFFICULTY_ENUM.EASY).length
})
const mediumQuestionNum = computed(() => {
  if (!questions.value?.length)
    return 0
  return questions.value.filter(i => i.difficulty === QUESTION_DIFFICULTY_ENUM.MEDIUM).length
})
const hardQuestionNum = computed(() => {
  if (!questions.value?.length)
    return 0
  return questions.value.filter(i => i.difficulty === QUESTION_DIFFICULTY_ENUM.HARD).length
})

const acceptedTotalNum = computed(() => acceptedData.value?.length)
const acceptedTotalPercent = computed(() => {
  const len = totalQuestionNum.value
  if (len <= 0)
    return 0
  if (acceptedTotalNum.value >= len)
    return 1
  return Number((acceptedTotalNum.value / len).toFixed(2))
})
const acceptedEasyNum = computed(() => {
  return acceptedData.value?.filter(i => i.questionId && questionDifficultyMap.value[i.questionId] === QUESTION_DIFFICULTY_ENUM.EASY).length || 0
})
const acceptedEasyPercent = computed(() => {
  const len = easyQuestionNum.value
  if (len <= 0)
    return 0
  if (acceptedEasyNum.value >= len)
    return 1
  return Number((acceptedEasyNum.value / len).toFixed(2))
})
const acceptedMediumNum = computed(() => {
  return acceptedData.value?.filter(i => i.questionId && questionDifficultyMap.value[i.questionId] === QUESTION_DIFFICULTY_ENUM.MEDIUM).length || 0
})
const acceptedMediumPercent = computed(() => {
  const len = mediumQuestionNum.value
  if (len <= 0)
    return 0
  if (acceptedMediumNum.value >= len)
    return 1
  return Number((acceptedMediumNum.value / len).toFixed(2))
})
const acceptedHardNum = computed(() => {
  return acceptedData.value?.filter(i => i.questionId && questionDifficultyMap.value[i.questionId] === QUESTION_DIFFICULTY_ENUM.HARD).length || 0
})
const acceptedHardPercent = computed(() => {
  const len = hardQuestionNum.value
  if (len <= 0)
    return 0
  if (acceptedHardNum.value >= len)
    return 1
  return Number((acceptedHardNum.value / len).toFixed(2))
})
</script>

<template>
  <div h-200px border border-base rounded flex="~ col" p-4>
    <div flex-y-center justify-between text-sm font-bold>
      <div text-sm font-bold>
        解题数量
      </div>
      <div flex-center gap-2 text-sm font-bold>
        <div> {{ acceptedTotalNum }} </div>  / <div>{{ totalQuestionNum }}</div>
      </div>
    </div>
    <div grid="~ cols-3" mt-3 flex-1 gap-2>
      <div col-span-1 flex-center>
        <a-progress
          :percent="acceptedTotalPercent"
          type="circle"
          size="large"
          :track-color="isDark ? 'rgb(var(--primary-9))' : 'rgb(var(--primary-2))'"
        />
      </div>
      <div col-span-2 grid="~ rows-3 gap-1">
        <div row-span-1 flex="~ col" gap-1>
          <div flex-y-center justify-between text-sm text-secondary>
            <div>简单</div>
            <div flex-center gap-2>
              <div text-base>
                {{ acceptedEasyNum }}
              </div>
              /
              <div>
                {{ easyQuestionNum }}
              </div>
            </div>
          </div>
          <a-progress size="large" :percent="acceptedEasyPercent" :show-text="false" color="#16a34a" :track-color="isDark ? '#14532d' : '#bbf7d0'" />
        </div>

        <div row-span-1 flex="~ col" gap-1>
          <div flex-y-center justify-between text-sm text-secondary>
            <div>中等</div>
            <div flex-center gap-2>
              <div text-base>
                {{ acceptedMediumNum }}
              </div>
              /
              <div>{{ mediumQuestionNum }}</div>
            </div>
          </div>
          <a-progress size="large" :percent="acceptedMediumPercent" :show-text="false" color="#ea580c" :track-color="isDark ? '#7c2d12' : '#ffedd5'" />
        </div>

        <div row-span-1 flex="~ col" gap-1>
          <div flex-y-center justify-between text-sm text-secondary>
            <div>困难</div>
            <div flex-center gap-2>
              <div text-base>
                {{ acceptedHardNum }}
              </div>
              /
              <div>{{ hardQuestionNum }}</div>
            </div>
          </div>
          <a-progress size="large" :percent="acceptedHardPercent" :show-text="false" color="#dc2626" :track-color="isDark ? '#7f1d1d' : '#fee2e2'" />
        </div>
      </div>
    </div>
  </div>
</template>

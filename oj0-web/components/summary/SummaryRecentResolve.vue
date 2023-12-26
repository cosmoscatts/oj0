<script setup lang="ts">
import type { SelectOptionData } from '@arco-design/web-vue'

const { userId } = defineProps<{
  userId?: string | number
}>()

const acceptedData = ref<QuestionSubmit[]>([])

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

function formatDate(record: QuestionSubmit) {
  if (!record?.createTime)
    return ''
  const date = record.createTime

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

const questionOptions = ref<SelectOptionData[]>([])
async function fetchQuestionOptions() {
  const { data: { records } } = await QuestionApi.listVo({})
  questionOptions.value = records?.map((i) => {
    return {
      value: i.id,
      label: i.title,
    }
  }) || []
}
fetchQuestionOptions()

const router = useRouter()
function checkoutQuestion(record: QuestionSubmit) {
  if (checkMySubmitRecord(record)) { // 自己提交的题目，可以查看代码
    router.push(`/resolve/${record.questionId}/${record.id}`)
  }
  else {
    router.push(`/resolve/${record.questionId}`)
  }
}

const refEl = ref(null)
const visible = useElementVisibility(refEl)
</script>

<template>
  <div min-h-200px min-w-500px border border-base rounded p-4>
    <div text-lg font-bold>
      最近通过
    </div>
    <div v-if="acceptedData.length" ref="refEl" flex="~ col" mt-4 min-h-120px gap-3>
      <template v-if="visible">
        <div
          v-for="item, idx in acceptedData" :key="item.id"
          :class="(idx & 1) === 0 ? 'bg-code' : ''" :style="{
            '--enter-stage': idx + 1,
            '--enter-step': '120ms',
          }"
          class="slide-enter" flex-y-center cursor-pointer justify-between rounded-10px px-6 py-4
          @click="checkoutQuestion(item)"
        >
          <CommonTooltip :content="`${item.questionId}.${getOptionsLabel(questionOptions, item.questionId)}`">
            <div>{{ getOptionsLabel(questionOptions, item.questionId) }}</div>
          </CommonTooltip>
          <div>{{ formatDate(item) }}</div>
        </div>
      </template>
    </div>
    <div v-else h-150px flex-center>
      您最近没有提交记录
    </div>
  </div>
</template>

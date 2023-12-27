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
    pageSize: -1,
  })
  acceptedData.value = records?.filter(i => i.judgeInfo && i.judgeInfo?.message === 'Accepted').slice(0, 10) || []
}
fetchAcceptedData()
watch(() => userId, fetchAcceptedData)

function formatDate(record: QuestionSubmit) {
  return formatDateAgo(record.createTime)
}

const questionOptions = ref<SelectOptionData[]>([])
async function fetchQuestionOptions() {
  const { data: { records } } = await QuestionApi.listVo({ pageSize: -1 })
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

const authStore = useAuthStore()
// 是否是本人
const isSelf = computed(() => userId === authStore.user?.id)
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
          :class="(idx & 1) === 0 ? 'bg-code hover:bg-primary-fade' : 'hover:bg-primary-fade'" :style="{
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
      {{ isSelf ? '您最近没有提交记录~' : 'TA最近还没有任何提交哦~' }}
    </div>
  </div>
</template>

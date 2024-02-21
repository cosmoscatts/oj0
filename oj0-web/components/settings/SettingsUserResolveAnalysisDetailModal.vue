<script setup lang="ts">
import type { SelectOptionData, TableColumnData } from '@arco-design/web-vue'

const { questionId, status } = defineProps<{
  questionId?: number | string
  status?: number
}>()
const visible = defineModel<boolean>()

const columns = [
  {
    title: '提交时间',
    dataIndex: 'createTime',
    slotName: 'createTime',
    align: 'center',
  },
  {
    title: '编程语言',
    dataIndex: 'language',
    slotName: 'language',
    align: 'center',
  },
  {
    title: '执行用时',
    dataIndex: 'useTime',
    slotName: 'useTime',
    align: 'center',
  },
  {
    title: '操作',
    dataIndex: 'action',
    slotName: 'action',
    align: 'center',
  },
] as TableColumnData[]

const details = ref<QuestionSubmit[]>([])
const { loading, startLoading, endLoading } = useLoading()
async function fetchSubmitList() {
  startLoading()
  const { data } = await ResolveAnalysisApi.listDetail({ questionId, status })
  details.value = data || []
  useTimeoutFn(endLoading, 1000)
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

watch(visible, (n) => {
  if (n)
    fetchSubmitList()
})

const title = computed(() => {
  return getOptionsLabel(questionOptions.value, questionId) || ''
})

const content = computed(() => {
  if (status === 0)
    return '提交通过详情'
  if (status === 1)
    return '提交不通过详情'
  return ''
})

const router = useRouter()
function checkoutQuestionSubmit(record: QuestionSubmit) {
  router.push(`/resolve/${record.questionId}/${record.id}`)
}
</script>

<template>
  <a-modal v-model:visible="visible" :footer="false" :closable="false" :width="600" :modal-style="{ borderRadius: '10px', minHeight: '200px' }">
    <div reactive>
      <div absolute right-3 top-3 rounded btn-action-icon @click="visible = false">
        <div i-ri-close-fill />
      </div>
    </div>

    <div flex text-xl font-bold>
      <div v-if="title !== ''" text-primary>
        {{ title }}.
      </div>
      <div>{{ content }}</div>
    </div>

    <div mt-3>
      <a-table
        :columns="columns"
        size="small"
        :loading="loading"
        :data="details"
        :bordered="{ wrapper: true, cell: true }"
      >
        <template #createTime="{ record }">
          {{ formatDate(record.createTime, 'YYYY-MM-DD HH:mm:ss') }}
        </template>
        <template #language="{ record }">
          <span select-none rounded-lg bg-dm px-3 py-1>
            {{ getOptionsLabel(questionResolveLanguageOptions, record.language) }}
          </span>
        </template>
        <template #useTime="{ record }">
          <div flex-center gap-1>
            <div i-ri-time-line />
            <template v-if="checkQuestionAccepted(record) && record.judgeInfo?.time">
              <div mt-0.5>
                {{ record.judgeInfo.time }} ms
              </div>
            </template>
            <template v-else>
              <div mt-0.5>
                NA
              </div>
            </template>
          </div>
        </template>
        <template #action="{ record }">
          <div py-1 btn-text @click="checkoutQuestionSubmit(record)">
            查看详情
          </div>
        </template>
      </a-table>
    </div>
  </a-modal>
</template>

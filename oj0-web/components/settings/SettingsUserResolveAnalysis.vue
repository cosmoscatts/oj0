<script setup lang="ts">
import type { TableColumnData } from '@arco-design/web-vue'
import { QUESTION_DIFFICULTY_ENUM } from '~/constants'

const statusOptions = Array.from({ length: 3 }, (_, idx) => {
  return {
    value: idx,
    label: ['已通过题目', '提交未通过', '未开始题目'][idx],
  }
})
const selectedStatus = ref(0) // 0: 已通过题目；1：提交未通过；2：未开始题目
const searchContent = ref('')

const defaultColumns = [
  {
    title: '最近提交时间',
    dataIndex: 'lastSubmitTime',
    slotName: 'lastSubmitTime',
    align: 'center',
  },
  {
    title: '题目',
    dataIndex: 'questionId',
    slotName: 'questionId',
    align: 'center',
  },
  {
    title: '题目难度',
    dataIndex: 'difficulty',
    slotName: 'difficulty',
    align: 'center',
  },
  {
    title: '提交次数',
    dataIndex: 'count',
    slotName: 'count',
    align: 'center',
  },
] as TableColumnData[]
const columns = computed(() => {
  if (selectedStatus.value === 2)
    return clone(defaultColumns).slice(1, 3)
  return clone(defaultColumns)
})

const tableData = ref<ResolveAnalysisItem[]>([])
const paginator = useTablePagination(search, { current: 1, pageSize: 20 })
const { loading, startLoading, endLoading } = useLoading()

function getSearchParams() {
  const { current, pageSize } = paginator.pagination
  return {
    current,
    pageSize,
    selectedStatus: selectedStatus.value,
    searchContent: searchContent.value,
  }
}

async function search() {
  startLoading()
  const { data: { records, total } } = await ResolveAnalysisApi.list(getSearchParams())
  tableData.value = records || []
  paginator.setPaginationTotal(Number(total || 0))
  useTimeoutFn(endLoading, 1000)
}
search()

watch(selectedStatus, search)

const router = useRouter()
function checkoutQuestion(record: ResolveAnalysisItem) {
  router.push(`/resolve/${record.questionId}`)
}

const { visible, data, show } = useVisible<ResolveAnalysisItem>()
</script>

<template>
  <div w-full grid="~ cols-3 gap-2">
    <div col-span-2>
      <div flex-y-center gap-3>
        <a-select v-model="selectedStatus" :options="statusOptions" w-144px />
        <a-input-search v-model="searchContent" placeholder="搜索题目 id" allow-clear w-210px @search="search" />
      </div>

      <a-table
        :columns="columns"
        size="small" mt-3
        :loading="loading"
        :data="tableData"
        :bordered="{ wrapper: true, cell: true }"
        :pagination="paginator.paginationProps.value"
        @page-change="paginator.onPageChange"
        @page-size-change="paginator.onPageSizeChange"
      >
        <template #lastSubmitTime="{ record }">
          {{ formatDateAgo(record.lastSubmitTime) }}
        </template>
        <template #questionId="{ record }">
          <CommonTooltip :content="`${record.questionId}.${record.title}`">
            <div cursor-pointer @click="checkoutQuestion(record)">
              {{ record.title }}
            </div>
          </CommonTooltip>
        </template>
        <template #difficulty="{ record }">
          <span v-if="record.difficulty === QUESTION_DIFFICULTY_ENUM.EASY" text-green>简单</span>
          <span v-if="record.difficulty === QUESTION_DIFFICULTY_ENUM.MEDIUM" text-orange>中等</span>
          <span v-if="record.difficulty === QUESTION_DIFFICULTY_ENUM.HARD" text-red>困难</span>
        </template>
        <template #count="{ record }">
          <div flex-center>
            <span mr-1 cursor-pointer text-primary @click="show(record)">
              {{ record.count || 0 }}
            </span>
            次
          </div>
        </template>
      </a-table>

      <SettingsUserResolveAnalysisDetailModal v-model="visible" :status="selectedStatus" :question-id="data?.questionId" />
    </div>
    <div col-span-1>
      WIP 完善中···
    </div>
  </div>
</template>

<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'SubmitHistory',
  title: '历史提交 - OJ0',
  access: ACCESS_ENUM.USER, // 需要用户权限
  middleware: 'auth',
})

const refSearchForm = ref()
const columns = getSubmitHistoryTableColumns()
const tableData = ref<QuestionSubmit[]>([])
const paginator = useTablePagination(search)
const { loading, startLoading, endLoading } = useLoading()

async function search() {
  startLoading()
  const { data: { records, total } } = await QuestionSubmitApi.list()
  tableData.value = records || []
  paginator.setPaginationTotal(Number(total || 0))
  useTimeoutFn(endLoading, 500)
}
onMounted(search)
</script>

<template>
  <CommonTableWrapper title="历史提交记录">
    <template #extra>
      <div />
    </template>
    <SubmitHistorySearchForm ref="refSearchForm" @search="search" />

    <a-divider />

    <a-table
      row-key="id"
      :columns="columns"
      :data="tableData"
      :loading="loading"
      :bordered="false"
      :pagination="paginator.paginationProps.value"
      @page-change="paginator.onPageChange"
      @page-size-change="paginator.onPageSizeChange"
    >
      <template #questionId="{ record }">
        <div hover="underline underline-offset-2 text-primary" cursor-pointer>
          {{ record.questionId }}. {{ record.questionTitle }}
        </div>
      </template>
      <template #language="{ record }">
        <span select-none rounded-lg bg-dm px-3 py-1>
          {{ getOptionsLabel(questionResolveLanguageOptions, record.language) }}
        </span>
      </template>
      <template #status="{ record }">
        <div flex-center>
          <div v-if="record.status === 0" flex-center gap-2>
            <div i-ri-loader-2-line animate-spin />
            等待中
          </div>
          <div v-if="record.status === 1" flex-center gap-2 text-orange>
            <div i-ri-loader-2-line animate-spin />
            判题中
          </div>
          <div v-else-if="record.status === 2" text-green>
            通过
          </div>
          <div v-else-if="record.status === 3" text-red>
            解答错误
          </div>
          <div v-else />
        </div>
      </template>
      <template #useTime="{ record }">
        <div flex-center gap-1>
          <div i-ri-time-line />
          <template v-if="record.useTime">
            <div mt-0.5>
              {{ record.useTime || 0 }} ms
            </div>
          </template>
          <template v-else>
            <div mt-0.5>
              NA
            </div>
          </template>
        </div>
      </template>
      <template #useMemory="{ record }">
        <div flex-center gap-1>
          <div i-ri-cpu-line />
          <template v-if="record.useMemory">
            <div mt-0.5>
              {{ record.useMemory || 0 }} MB
            </div>
          </template>
          <template v-else>
            <div mt-0.5>
              NA
            </div>
          </template>
        </div>
      </template>
      <template #userId>
        狂雪
      </template>
      <template #createTime="{ record }">
        <div>
          {{ formatDate(record.createTime, 'YYYY-MM-DD HH:mm:ss') }}
        </div>
      </template>
    </a-table>
  </CommonTableWrapper>
</template>

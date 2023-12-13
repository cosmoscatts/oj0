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
const { loading } = useLoading()

const paginator = useTablePagination(search)

function search() {
  tableData.value = Array.from({ length: 6 }, (_, idx) => {
    const questionNames = ['下一个更大元素 IV', '两数之和', '最长回文子串']
    const questionId = getRandomInteger(3) + 1
    return {
      id: idx + 1,
      questionId,
      questionTitle: questionNames[questionId - 1],
      language: 'java',
      state: getRandomInteger(3),
      useMemory: Math.random() < 0.6 ? getRandomInteger(100) : undefined,
      useTime: Math.random() < 0.6 ? getRandomInteger(100) : undefined,
      userId: 1,
      createTime: new Date(),
    }
  })
  paginator.setPaginationTotal(6)
}
search()
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
      <template #state="{ record }">
        <div flex-center>
          <div v-if="record.state === 1" text-red>
            解答错误
          </div>
          <div v-else-if="record.state === 2" text-green>
            通过
          </div>
          <div v-else flex-center gap-2>
            <div i-ri-loader-2-line animate-spin />
            等待中
          </div>
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

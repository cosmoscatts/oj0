<script setup lang="ts">
import type { TableColumnData } from '@arco-design/web-vue'

const { id } = defineProps<{ id: string }>()

const columns: TableColumnData[] = [
  {
    title: '状态',
    dataIndex: 'status',
    slotName: 'status',
    align: 'center',
  },
  {
    title: '语言',
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
    title: '消耗内存',
    dataIndex: 'useMemory',
    slotName: 'useMemory',
    align: 'center',
  },
]

const authStore = useAuthStore()
const tableData = ref<QuestionSubmit[]>([])

async function fetchData() {
  const searchParams = {
    userId: authStore.user?.id,
    questionId: id,
    sortField: 'createTime',
    sortOrder: 'descend',
  }
  const { data: { records } } = await QuestionSubmitApi.list(searchParams)
  tableData.value = records || []
}
fetchData()

function onRowClick() {

}
</script>

<template>
  <div min-w-400px of-auto py-10px>
    <a-table :columns="columns" :data="tableData" :pagination="false" :bordered="false" row-class="cursor-pointer" @row-click="onRowClick">
      <template #status="{ record }">
        <div grid="~ rows-2 gap-1" text-sm>
          <div row-span-1 flex-center>
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
          <CommonTooltip :content="formatDate(record.createTime, 'YYYY-MM-DD HH:mm:ss')">
            <div row-span-1 flex-center>
              {{ formatDate(record.createTime, 'YYYY-MM-DD') }}
            </div>
          </CommonTooltip>
        </div>
      </template>
      <template #language="{ record }">
        <span select-none rounded-lg bg-dm px-3 py-1>
          {{ getOptionsLabel(questionResolveLanguageOptions, record.language) }}
        </span>
      </template>
      <template #useTime="{ record }">
        <div flex-center gap-1>
          <div i-ri-time-line />
          <template v-if="record.judgeInfo?.time">
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
      <template #useMemory="{ record }">
        <div flex-center gap-1>
          <div i-ri-cpu-line />
          <template v-if="record.judgeInfo?.memory">
            <div mt-0.5>
              {{ record.judgeInfo.memory }} MB
            </div>
          </template>
          <template v-else>
            <div mt-0.5>
              NA
            </div>
          </template>
        </div>
      </template>
    </a-table>
  </div>
</template>

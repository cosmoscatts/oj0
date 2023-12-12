<script setup lang="ts">
import type { TableColumnData } from '@arco-design/web-vue'

const columns: TableColumnData[] = [
  {
    title: '状态',
    dataIndex: 'state',
    slotName: 'state',
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

const tableData = ref<{
  state: number
  language: string
  useTime?: number
  useMemory?: number
  createTime?: Date
}[]>([])

function fetchData() {
  tableData.value = Array.from({ length: 3 }, () => {
    return {
      state: getRandomInteger(3),
      language: 'java',
      useTime: getRandomInteger(2) < 1 ? undefined : getRandomInteger(20, 1),
      useMemory: getRandomInteger(50, 20),
      createTime: new Date(),
    }
  })
}
fetchData()

function onRowClick() {

}
</script>

<template>
  <div min-w-400px of-auto py-10px>
    <a-table :columns="columns" :data="tableData" :pagination="false" :bordered="false" row-class="cursor-pointer" @row-click="onRowClick">
      <template #state="{ record }">
        <div grid="~ rows-2 gap-1" text-sm>
          <div row-span-1 flex-center>
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
    </a-table>
  </div>
</template>

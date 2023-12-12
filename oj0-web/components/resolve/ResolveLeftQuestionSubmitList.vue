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
    dataIndex: 'time',
    slotName: 'time',
    align: 'center',
  },
  {
    title: '消耗内存',
    dataIndex: 'memory',
    slotName: 'memory',
    align: 'center',
  },
]

const tableData = ref<{
  state: number
  language: string
  time?: number
  memory?: number
  createTime?: Date
}[]>([])

function fetchData() {
  tableData.value = Array.from({ length: 3 }, () => {
    return {
      state: getRandomInteger(2),
      language: 'java',
      time: getRandomInteger(2) < 1 ? undefined : getRandomInteger(20, 1),
      memory: getRandomInteger(50, 20),
      createTime: new Date(),
    }
  })
}
fetchData()

function onRowClick() {

}
</script>

<template>
  <div min-w-300px of-auto py-10px>
    <a-table :columns="columns" :data="tableData" :bordered="false" row-class="cursor-pointer" @row-click="onRowClick">
      <template #state="{ record }">
        <div grid="~ rows-2 gap-1" text-sm>
          <div row-span-1 flex-center>
            <div v-if="record.state === 1" text-green>
              通过
            </div>
            <div v-else text-red>
              解答错误
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
        {{ getOptionsLabel(questionResolveLanguageOptions, record.language) }}
      </template>
      <template #time="{ record }">
        <div flex-center gap-1>
          <div i-ri-time-line />
          <template v-if="record.time">
            <div mt-0.5>
              {{ record.time || 0 }} ms
            </div>
          </template>
          <template v-else>
            <div mt-0.5>
              NA
            </div>
          </template>
        </div>
      </template>
      <template #memory="{ record }">
        <div flex-center gap-1>
          <div i-ri-cpu-line />
          <template v-if="record.memory">
            <div mt-0.5>
              {{ record.memory || 0 }} MB
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

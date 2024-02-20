<script setup lang="ts">
import type { TableColumnData } from '@arco-design/web-vue'

const statusOptions = Array.from({ length: 3 }, (_, idx) => {
  return {
    value: idx,
    label: ['已通过题目', '提交未通过', '未开始题目'][idx],
  }
})
const selectedStatus = ref(0) // 0: 已通过题目；1：提交未通过；2：未开始题目
const searchContent = ref('')

const columns = [
  {
    title: '最近提交时间',
    dataIndex: 'name',
    slotName: '',
    align: 'center',
  },
  {
    title: '题目',
    dataIndex: 'salary',
    slotName: '',
    align: 'center',
  },
  {
    title: '题目难度',
    dataIndex: 'address',
    slotName: '',
    align: 'center',
  },
  {
    title: '提交次数',
    dataIndex: 'count',
    slotName: 'count',
    align: 'center',
  },
] as TableColumnData[]

const tableData = ref([])

function search() {

}
</script>

<template>
  <div w-full grid="~ cols-3 gap-2">
    <div col-span-2>
      <div flex-y-center gap-3>
        <a-select v-model="selectedStatus" :options="statusOptions" w-144px />
        <a-input-search v-model="searchContent" placeholder="搜索题目" allow-clear w-210px @search="search" />
      </div>

      <a-table :columns="columns" size="small" :data="tableData" :bordered="{ wrapper: true, cell: true }" mt-3 />
    </div>
    <div col-span-1>
      WIP 完善中···
    </div>
  </div>
</template>

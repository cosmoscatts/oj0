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

const expandable = reactive({
  title: '',
  width: 60,
})

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

const data = reactive([
  {
    key: '1',
    name: 'Jane Doe',
    salary: 23000,
    address: '32 Park Road, London',
    email: 'jane.doe@example.com',
    expand: 'Expand Data',
  },
  {
    key: '2',
    name: 'Alisa Ross',
    salary: 25000,
    address: '35 Park Road, London',
    email: 'alisa.ross@example.com',
  },
  {
    key: '3',
    name: 'Kevin Sandra',
    salary: 22000,
    address: '31 Park Road, London',
    email: 'kevin.sandra@example.com',
  },
  {
    key: '4',
    name: 'Ed Hellen',
    salary: 17000,
    address: '42 Park Road, London',
    email: 'ed.hellen@example.com',
  },
  {
    key: '5',
    name: 'William Smith',
    salary: 27000,
    address: '62 Park Road, London',
    email: 'william.smith@example.com',
  },
])

const expandColumns: TableColumnData[] = []
const expandTableData = []

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

      <a-table :columns="columns" size="small" :data="data" :bordered="{ wrapper: true, cell: true }" :expandable="expandable" mt-3>
        <template #expand-row="{ record }">
          <a-table :columns="expandColumns" size="small" :data="expandTableData" />
        </template>
        <template #count="{ record }">
          {{ record.email }}
          <div />
        </template>
      </a-table>
    </div>
    <div col-span-1>
      11
    </div>
  </div>
</template>

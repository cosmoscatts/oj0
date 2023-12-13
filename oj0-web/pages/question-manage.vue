<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'QuestionManage',
  title: '题目管理 - OJ0',
  access: ACCESS_ENUM.ADMIN,
  middleware: 'auth',
})

const refSearchForm = ref()

const columns = getQuestionManageTableColumns()

const tableData = ref<Question[]>([])
const { loading } = useLoading()

const paginator = useTablePagination(search)

function search() {
  tableData.value = Array.from({ length: 6 }, (_, idx) => {
    return {
      id: idx + 1,
      title: `题目${idx + 1}`,
      content: '哈哈哈哈哈哈哈',
      tags: ['简单', '二叉树'],
      createTime: new Date(),
      updateTime: new Date(),
    }
  })
  paginator.setPaginationTotal(6)
}
search()

const { visible, data, show } = useVisible<Partial<User>>()

function add() {
  show()
}
</script>

<template>
  <CommonTableWrapper title="题目管理" @add="add">
    <QuestionManageSearchForm ref="refSearchForm" @search="search" />

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
      <template #tags="{ record }">
        <div v-if="record.tags.length" w-200px>
          <a-overflow-list>
            <a-tag v-for="tag in record.tags" :key="tag" bordered color="transparent" :style="{ color: 'var(--c-text-base)' }">
              {{ tag }}
            </a-tag>
          </a-overflow-list>
        </div>
      </template>
      <template #userId>
        管理员
      </template>
      <template #createTime="{ record }">
        {{ formatDate(record.createTime) }}
      </template>
      <template #updateTime="{ record }">
        {{ formatDate(record.updateTime) }}
      </template>
      <template #action="{ record }">
        <div flex-center>
          <CommonTooltip content="编辑题目">
            <div btn-text @click="show(record)">
              编辑
            </div>
          </CommonTooltip>
          <CommonTooltip content="删除题目">
            <div filter-saturate-0 btn-text>
              删除
            </div>
          </CommonTooltip>
        </div>
      </template>
    </a-table>

    <QuestionManageFormDrawer v-model="visible" :data="data" />
  </CommonTableWrapper>
</template>

<script setup lang="ts">
import { ACCESS_ENUM, QUESTION_DIFFICULTY_ENUM } from '~/constants'

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

async function search() {
  const { current, pageSize } = paginator.pagination
  const searchParams = {
    current,
    pageSize,
    ...refSearchForm.value?.getSearchParams(),
  }
  const { data: { records, total } } = await QuestionApi.list(searchParams)
  tableData.value = transformQuestionToVO(records)
  paginator.setPaginationTotal(Number(total || 0))
}
search()

const { visible, data, show, close } = useVisible<Partial<Question>>()

function add() {
  show()
}

function remove({ id }: Question) {
  useConfirm({
    title: '删除确认',
    content: '确定要删除该题目吗？',
    ok: async () => {
      const { code, message } = await QuestionApi.delete({ id })
      if (code !== 0) {
        Message.error(message || '删除失败')
        return
      }
      Message.success('删除成功')
      search()
    },
  })
}

async function saveData(data: Partial<Question>) {
  const isAdd = !data.id
  const text = ['更新', '新增'][Number(isAdd)]
  const fn = isAdd ? QuestionApi.add : QuestionApi.update
  const { code, message } = await fn(data)
  if (code !== 0) {
    Message.error(message || `${text}失败`)
    return
  }
  Message.success(`${text}成功`)
  batchInvoke([close, search])
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
      <template #id="{ rowIndex }">
        {{ paginator.formatRowIndex(rowIndex) }}
      </template>
      <template #difficulty="{ record }">
        <span v-if="record.difficulty === QUESTION_DIFFICULTY_ENUM.EASY" text-green>简单</span>
        <span v-if="record.difficulty === QUESTION_DIFFICULTY_ENUM.MEDIUM" text-orange>中等</span>
        <span v-if="record.difficulty === QUESTION_DIFFICULTY_ENUM.HARD" text-red>困难</span>
      </template>
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
        <span select-none rounded-lg bg-dm px-3 py-1>
          管理员
        </span>
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
            <div filter-saturate-0 btn-text @click="remove(record)">
              删除
            </div>
          </CommonTooltip>
        </div>
      </template>
    </a-table>

    <QuestionManageFormDrawer v-model="visible" :data="data" @save="saveData" />
  </CommonTableWrapper>
</template>

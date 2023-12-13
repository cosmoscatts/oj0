<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'UserManage',
  title: '用户管理 - OJ0',
  access: ACCESS_ENUM.ADMIN,
  middleware: 'auth',
})

const refSearchForm = ref()

const columns = getUserManageTableColumns()

const tableData = ref<User[]>([])
const { loading } = useLoading()

const paginator = useTablePagination(search)

async function search() {
  const { current, pageSize } = paginator.pagination
  const searchParams = {
    current,
    pageSize,
    ...refSearchForm.value?.getSearchParams(),
  }
  const { data: { records, total } } = await UserApi.list(searchParams)
  tableData.value = records || []
  paginator.setPaginationTotal(total || 0)
}
search()

const { visible, data, show } = useVisible<Partial<User>>()

function add() {
  show()
}

function remove({ id }: User) {
  useConfirm({
    title: '删除确认',
    content: '确定要删除该用户吗？',
    ok: async () => {
      const { code, message } = await UserApi.delete({ id })
      if (code !== 0) {
        Message.error(message || '删除失败')
        return
      }
      Message.success('删除成功')
      search()
    },
  })
}
</script>

<template>
  <CommonTableWrapper title="用户管理" @add="add">
    <UserManageSearchForm ref="refSearchForm" @search="search" />

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
      <template #userRole="{ record }">
        <span select-none rounded-lg bg-dm px-3 py-1>
          {{ getOptionsLabel(userRoleOptions, record.userRole) }}
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
          <CommonTooltip content="编辑用户">
            <div btn-text @click="show(record)">
              编辑
            </div>
          </CommonTooltip>
          <CommonTooltip content="删除用户">
            <div filter-saturate-0 btn-text @click="remove(record)">
              删除
            </div>
          </CommonTooltip>
        </div>
      </template>
    </a-table>

    <UserManageFormDrawer v-model="visible" :data="data" />
  </CommonTableWrapper>
</template>

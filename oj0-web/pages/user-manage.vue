<script setup lang="ts">
const refSearchForm = ref()

const columns = getUserManageTableColumns()

const tableData = ref<User[]>([])
const { loading } = useLoading()

const paginator = useTablePagination(search)

function search() {
  tableData.value = Array.from({ length: 6 }, (_, idx) => {
    return {
      id: idx + 1,
      userAccount: getRandomStr(10),
      userName: getRandomStr(10),
    }
  })
  paginator.setPaginationTotal(6)
}
search()

const { visible, data, show } = useVisible<Partial<User>>()

function add() {
  show()
}

function remove(_record: User) {
  useConfirm({
    title: '删除确认',
    content: '确定要删除该用户吗？',
    ok: () => {
      Message.success('删除成功')
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
      <template #action="{ record }">
        <div flex-center>
          <div btn-text @click="show(record)">
            编辑
          </div>
          <div filter-saturate-0 btn-text @click="remove(record)">
            删除
          </div>
        </div>
      </template>
    </a-table>

    <UserManageFormDrawer v-model="visible" :data="data" />
  </CommonTableWrapper>
</template>

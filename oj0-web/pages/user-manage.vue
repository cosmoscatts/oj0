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

const drawerVisible = ref(false)

function add() {
  drawerVisible.value = true
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
    />

    <UserManageFormDrawer v-model="drawerVisible" />
  </CommonTableWrapper>
</template>
